package it.dziubinski.workInIt.cron.noFluffJobsCom

import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class NoFluffJobsComCount(
    val totalCount: Int,
    val totalPages: Int,
)

@Component
class NoFluffJobsComCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: NoFluffJobsComRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder, JobPortal.NO_FLUFF_JOBS_COM) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<NoFluffJobsComCount>(responseData).totalCount
    }
}