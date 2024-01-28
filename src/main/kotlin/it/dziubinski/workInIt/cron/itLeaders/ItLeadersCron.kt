package it.dziubinski.workInIt.cron.itLeaders

import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class ItLeadersResponse(
    val records: Int,
)

@Component
class ItLeadersCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: ItLeadersRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder, JobPortal.IT_LEADERS) {

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