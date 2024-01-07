package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.extensions.cUrlString
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class IndeedComCron(
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val indeedComRequestBuilder: IndeedComRequestBuilder,
) {

    @Scheduled(cron = "0 6 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:06:00
    fun getTotalOffersNumber() {
        for (jobCategory in JobCategory.entries) {
            getOfferNumberForJobCategory(jobCategory)
        }
    }

    private fun getOfferNumberForJobCategory(jobCategory: JobCategory) {
        val cities = listOf(null, "Warsaw")
        for (city in cities) {
            val offerCount = scrapWebPageCountOffer(JobPortal.INDEED_COM, jobCategory, city)
            val jobOfferCount = JobOfferCount(JobPortal.INDEED_COM, offerCount, jobCategory, city)
            jobOfferCountRepository.save(jobOfferCount)
            Thread.sleep(1_000)
        }
    }

    fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber)
    }

    fun scrapWebPageCountOffer(jobPortal: JobPortal, jobCategory: JobCategory, city: String?): Int {
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.addArguments("--disable-gpu")
        firefoxOptions.addArguments("--headless")
        val driver = FirefoxDriver(firefoxOptions)

        val request = indeedComRequestBuilder.apply { this.jobCategory = jobCategory; this.city = city }.build()
        println(request.cUrlString())
        driver.get(request.url.toString())

        val findElement = WebDriverWait(driver, Duration.ofSeconds(2)).until(
            ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(
                    ".jobsearch-JobCountAndSortPane-jobCount > span"
                )
            )
        )
        val count = findElement.text.filter { it.isDigit() }.toInt()
        driver.close()

        return count
    }
}