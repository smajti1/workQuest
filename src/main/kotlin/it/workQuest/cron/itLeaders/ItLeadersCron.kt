package it.workQuest.cron.itLeaders

import it.workQuest.cron.JobOfferRequestCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class ItLeadersResponse(
    val records: Int,
)

@Component
class ItLeadersCron(
    jobOfferCountService: JobOfferCountService,
    urlBuilder: ItLeadersRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountService, urlBuilder, JobPortal.IT_LEADERS) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<ItLeadersResponse>(responseData).records
    }
}