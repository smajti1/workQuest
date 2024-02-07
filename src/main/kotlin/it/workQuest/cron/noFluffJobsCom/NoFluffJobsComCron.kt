package it.workQuest.cron.noFluffJobsCom

import it.workQuest.cron.JobOfferRequestCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
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
    jobOfferCountService: JobOfferCountService,
    urlBuilder: NoFluffJobsComRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountService, urlBuilder, JobPortal.NO_FLUFF_JOBS_COM) {

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