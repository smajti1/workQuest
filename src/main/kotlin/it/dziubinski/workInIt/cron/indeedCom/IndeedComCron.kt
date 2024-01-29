package it.dziubinski.workInIt.cron.indeedCom

import it.dziubinski.workInIt.cron.JobOfferScrapWebPageCronAbstract
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.service.JobOfferCountService
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class IndeedComCron(
    jobOfferCountService: JobOfferCountService,
    indeedComRequestBuilder: IndeedComRequestBuilder,
) : JobOfferScrapWebPageCronAbstract(jobOfferCountService, indeedComRequestBuilder, JobPortal.INDEED_COM) {

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
                    ".jobsearch-JobCountAndSortPane-jobCount > span"
                )
            )
        )
        val count = findElement.text.filter { it.isDigit() }.toInt()
        return count
    }
}