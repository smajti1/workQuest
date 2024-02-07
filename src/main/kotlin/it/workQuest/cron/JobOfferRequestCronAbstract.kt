package it.workQuest.cron

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.result.Result
import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService

abstract class JobOfferRequestCronAbstract(
    private val jobOfferCountService: JobOfferCountService,
    private val urlBuilder: RequestBuilderInterface,
    private val jobPortal: JobPortal,
    private var sleepTime: Long = 1_000,
) : JobOfferCronInterface {

    protected fun createRequestsForJobPortalAndCategoryByCities(sleepTime: Long) {
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
            val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; this.city = city }.build()
            sendResponseAndCreateJobOfferCountEntity(urlTotal, jobCategory, city)
            if (this.sleepTime > 0) {
                Thread.sleep(this.sleepTime)
            }
        }
    }

    private fun sendResponseAndCreateJobOfferCountEntity(
        request: Request,
        jobCategory: JobCategory,
        city: String?,
    ) {
        println(request.cUrlString())
        request.responseString { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value
                    val jobOfferCount = getCountFromRequest(data)
                    jobOfferCountService.saveNewJobOfferCount(this.jobPortal, jobCategory, city, jobOfferCount)
                }

                is Result.Failure -> {
                    val ex = result.error.exception
                    println("Error: $ex")
                }
            }
        }
    }

    abstract fun getCountFromRequest(responseData: String): Int
}