package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.result.Result
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository

abstract class JobOfferCronAbstract(
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val urlBuilder: RequestBuilderInterface,
) {

    protected fun createRequestsForJobPortalAndCategoryByCities(jobPortal: JobPortal, jobCategory: JobCategory) {
        val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; city = null }.build()
        sendResponseAndCreateJobOfferCountEntity(urlTotal, jobPortal, jobCategory, null)
        val city = "Warsaw"
        val urlWarsaw = urlBuilder.apply { this.city = city }.build()
        sendResponseAndCreateJobOfferCountEntity(urlWarsaw, jobPortal, jobCategory, city)
    }

    private fun sendResponseAndCreateJobOfferCountEntity(
        request: Request,
        jobPortal: JobPortal,
        jobCategory: JobCategory,
        city: String?,
    ) {
        println(request.cUrlString())
        request.responseString { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value
                    val jobOfferCount = getCountFromRequest(data)
                    saveNewJobOfferCount(jobPortal, jobCategory, city, jobOfferCount)
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

    abstract fun getCronFunctionArray(): Array<() -> Unit>

    abstract fun getCountFromRequest(responseData: String): Int
}