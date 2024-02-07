package it.workQuest.cron.theProtocol

import it.workQuest.cron.JobOfferScrapWebPageCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class TheProtocolCron(
    jobOfferCountService: JobOfferCountService,
    theProtocolRequestBuilder: TheProtocolRequestBuilder,
) : JobOfferScrapWebPageCronAbstract(jobOfferCountService, theProtocolRequestBuilder, JobPortal.THE_PROTOCOL) {

    fun getOffersNumber(sleepTime: Long) {
        scrapWebPageForJobPortalAndCategoryByCities(sleepTime)
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