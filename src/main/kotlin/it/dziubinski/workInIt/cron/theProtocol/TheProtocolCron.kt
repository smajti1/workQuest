package it.dziubinski.workInIt.cron.theProtocol

import it.dziubinski.workInIt.cron.JobOfferScrapWebPageCronAbstract
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration

@Component
@EnableScheduling
class TheProtocolCron(
    jobOfferCountRepository: JobOfferCountRepository,
    theProtocolRequestBuilder: TheProtocolRequestBuilder,
) : JobOfferScrapWebPageCronAbstract(jobOfferCountRepository, theProtocolRequestBuilder) {

    @Scheduled(cron = "0 7 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:07:00
    fun getTotalOffersNumber() {
        for (jobCategory in JobCategory.entries) {
            getOfferNumberForJobCategory(jobCategory)
        }
    }

    private fun getOfferNumberForJobCategory(jobCategory: JobCategory) {
        val cities = listOf(null, "Warsaw")
        for (city in cities) {
            scrapWebPageCountOffer(JobPortal.THE_PROTOCOL, jobCategory, city)
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
                    "[data-test=\"text-offersCount-label\"]"
                )
            )
        )
        val count = findElement.text.filter { it.isDigit() }.toInt()
        return count
    }
}