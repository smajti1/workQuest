package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.result.Result
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

abstract class JobOfferRequestCronAbstract(
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val urlBuilder: RequestBuilderInterface,
    private val jobPortal: JobPortal,
    private var sleepTime: Long = 1_000,
) : JobOfferCronInterface {

    protected fun createRequestsForJobPortalAndCategoryByCities(sleepTime: Long) {
        this.sleepTime = sleepTime
        val jobPortalCitiesByCategory = getJobPortalAlreadyCreatedCitiesByCategoryMap(this.jobPortal)
        for (jobCategory in JobCategory.entries) {
            if (jobPortalCitiesByCategory[jobCategory]?.count() == JobOfferCount.cities.count()) {
                continue
            }
            getOfferNumberForJobCategory(jobCategory, jobPortalCitiesByCategory[jobCategory] ?: emptyList())
        }
    }

    private fun getJobPortalAlreadyCreatedCitiesByCategoryMap(jobPortal: JobPortal): Map<JobCategory, List<String?>> {
        val now = LocalDate.now()
        val startOfDay = LocalDateTime.of(now, LocalTime.MIN)
        val endOfDay = LocalDateTime.of(now, LocalTime.MAX)
        val jobPortalCountList = jobOfferCountRepository.findByJobPortalAndCreatedAtBetween(jobPortal, startOfDay, endOfDay)

        return jobPortalCountList.groupBy({ it.category }, { it.city })
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
                    saveNewJobOfferCount(this.jobPortal, jobCategory, city, jobOfferCount)
                }

                is Result.Failure -> {
                    val ex = result.error.exception
                    println("Error: $ex")
                }
            }
        }
    }

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

    abstract fun getCountFromRequest(responseData: String): Int
}