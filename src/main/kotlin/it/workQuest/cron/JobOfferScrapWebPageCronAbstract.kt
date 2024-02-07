package it.workQuest.cron

import com.github.kittinunf.fuel.core.extensions.cUrlString
import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI

const val SELENIUM_URL = "http://selenium-web:4444/wd/hub"

abstract class JobOfferScrapWebPageCronAbstract(
    private val jobOfferCountService: JobOfferCountService,
    private val urlBuilder: RequestBuilderInterface,
    private val jobPortal: JobPortal,
    private var sleepTime: Long = 1_000,
) : JobOfferCronInterface {

    fun scrapWebPageForJobPortalAndCategoryByCities(sleepTime: Long) {
        this.sleepTime = sleepTime
        val jobPortalCitiesByCategory = jobOfferCountService.getJobPortalAlreadyCreatedCitiesByCategoryMap(this.jobPortal)
        for (jobCategory in JobCategory.entries) {
            if (jobPortalCitiesByCategory[jobCategory]?.count() == JobOfferCount.cities.count()) {
                continue
            }

            getOfferNumberForJobCategory(jobCategory, jobPortalCitiesByCategory[jobCategory] ?: emptyList())
        }
    }

    private fun getOfferNumberForJobCategory(jobCategory: JobCategory, citiesToSkip: List<String?>) {
        for (city in JobOfferCount.cities) {
            if (citiesToSkip.contains(city)) {
                continue
            }
            scrapWebPageCountOffer(jobCategory, city)
            if (this.sleepTime > 0) {
                Thread.sleep(this.sleepTime)
            }
        }
    }

    private fun scrapWebPageCountOffer(jobCategory: JobCategory, city: String?) {
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
        options.addArguments("--ignore-certificate-errors")
        options.addArguments("--ignore-ssl-errors")

        val driver = RemoteWebDriver(URI(SELENIUM_URL).toURL(), options)
        driver.get(request.url.toString())

        val offerCount = getCountFromWebPage(driver)
        jobOfferCountService.saveNewJobOfferCount(this.jobPortal, jobCategory, city, offerCount)
        // val screenshot = driver.getScreenshotAs(OutputType.FILE)
        // FileUtils.copyFile(screenshot, File("screenshots/${LocalDate.now()}-${jobPortal}-${jobCategory}-${city}.png"))

        driver.quit()
    }

    abstract fun getCountFromWebPage(driver: WebDriver): Int
}