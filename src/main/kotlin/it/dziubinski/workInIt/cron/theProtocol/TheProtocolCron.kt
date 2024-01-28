package it.dziubinski.workInIt.cron.theProtocol

import it.dziubinski.workInIt.cron.JobOfferScrapWebPageCronAbstract
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class TheProtocolCron(
    jobOfferCountRepository: JobOfferCountRepository,
    theProtocolRequestBuilder: TheProtocolRequestBuilder,
) : JobOfferScrapWebPageCronAbstract(jobOfferCountRepository, theProtocolRequestBuilder) {

    fun getOffersNumber(sleepTime: Long) {
        for (jobCategory in JobCategory.entries) {
            getOfferNumberForJobCategory(jobCategory, sleepTime)
        }
    }

    private fun getOfferNumberForJobCategory(jobCategory: JobCategory, sleepTime: Long) {
        for (city in JobOfferCount.cities) {
            scrapWebPageCountOffer(JobPortal.THE_PROTOCOL, jobCategory, city)
            if (sleepTime > 0) {
                Thread.sleep(sleepTime)
            }
        }
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromWebPage(driver: WebDriver): Int {
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