package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.extensions.cUrlString
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

abstract class JobOfferScrapWebPageCronAbstract(
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val urlBuilder: RequestBuilderInterface,
) {

    fun scrapWebPageCountOffer(jobPortal: JobPortal, jobCategory: JobCategory, city: String?) {
        val firefoxOptions = FirefoxOptions()
        firefoxOptions.addArguments("--disable-gpu")
        firefoxOptions.addArguments("--headless")
        val driver = FirefoxDriver(firefoxOptions)

        val request = urlBuilder.apply { this.jobCategory = jobCategory; this.city = city }.build()
        println(request.cUrlString())
        driver.get(request.url.toString())

        val offerCount = getCountFromWebPage(driver)
        saveNewJobOfferCount(jobPortal, jobCategory, city, offerCount)

        driver.close()
    }

    abstract fun getCronFunctionArray(): Array<() -> Unit>

    abstract fun getCountFromWebPage(driver: FirefoxDriver): Int

    private fun saveNewJobOfferCount(
        jobPortal: JobPortal,
        category: JobCategory,
        city: String?,
        offerCount: Int,
    ): JobOfferCount {
        val jobOfferCount = JobOfferCount(jobPortal, offerCount, category, city)
        jobOfferCountRepository.save(jobOfferCount)

        return jobOfferCount
    }
}