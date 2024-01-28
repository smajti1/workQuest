package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.extensions.cUrlString
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI

const val SELENIUM_URL = "http://selenium-web:4444/wd/hub"

abstract class JobOfferScrapWebPageCronAbstract(
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val urlBuilder: RequestBuilderInterface,
) : JobOfferCronInterface {

    fun scrapWebPageCountOffer(jobPortal: JobPortal, jobCategory: JobCategory, city: String?) {
        val request = urlBuilder.apply { this.jobCategory = jobCategory; this.city = city }.build()
        println(request.cUrlString())

        val options = ChromeOptions()
        // https://chromium.googlesource.com/chromium/src/+/refs/tags/122.0.6261.4/components/content_settings/core/common/pref_names.h
        val prefs = mapOf(
            "profile.managed_default_content_settings.images" to 2, // block image loading
        )
        options.setExperimentalOption("prefs", prefs)
        options.addArguments("window-size=1280,600")
        options.addArguments("disable-infobars")
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-extensions")
        options.addArguments("--disable-application-cache")
        options.addArguments("--disable-dev-shm-usage")
        options.addArguments("--disable-extensions")
        options.addArguments("--disable-gpu")

        val driver = RemoteWebDriver(URI(SELENIUM_URL).toURL(), options)
        driver.get(request.url.toString())

        val offerCount = getCountFromWebPage(driver)
        saveNewJobOfferCount(jobPortal, jobCategory, city, offerCount)
        // val screenshot = driver.getScreenshotAs(OutputType.FILE)
        // FileUtils.copyFile(screenshot, File("screenshots/${LocalDate.now()}-${jobPortal}-${jobCategory}-${city}.png"))

        driver.quit()
    }

    abstract fun getCountFromWebPage(driver: WebDriver): Int

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