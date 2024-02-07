package it.workQuest.cron.startupJobs

import it.workQuest.cron.JobOfferRequestCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class StartupJobsCronRequest(
    val resultCount: Int,
)

@Component
class StartupJobsCron(
    jobOfferCountService: JobOfferCountService,
    urlBuilder: StartupJobsRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountService, urlBuilder, JobPortal.STARTUP_JOBS) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<StartupJobsCronRequest>(responseData).resultCount
    }
}