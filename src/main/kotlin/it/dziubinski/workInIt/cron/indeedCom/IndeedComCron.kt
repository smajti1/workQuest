package it.dziubinski.workInIt.cron.indeedCom

import it.dziubinski.workInIt.cron.JobOfferScrapWebPageCronAbstract
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class IndeedComCron(
    jobOfferCountRepository: JobOfferCountRepository,
    indeedComRequestBuilder: IndeedComRequestBuilder,
) : JobOfferScrapWebPageCronAbstract(jobOfferCountRepository, indeedComRequestBuilder) {

    @Scheduled(cron = "0 6 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:06:00
    fun getTotalOffersNumber() {
        for (jobCategory in JobCategory.entries) {
            getOfferNumberForJobCategory(jobCategory)
        }
    }

    private fun getOfferNumberForJobCategory(jobCategory: JobCategory) {
        val cities = listOf(null, "Warsaw")
        for (city in cities) {
            scrapWebPageCountOffer(JobPortal.INDEED_COM, jobCategory, city)
            Thread.sleep(1_000)
        }
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber)
    }

    override fun getCountFromWebPage(driver: FirefoxDriver): Int {
        val findElement = WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    ".jobsearch-JobCountAndSortPane-jobCount > span"
                )
            )
        )
        val count = findElement.text.filter { it.isDigit() }.toInt()
        return count
    }
}