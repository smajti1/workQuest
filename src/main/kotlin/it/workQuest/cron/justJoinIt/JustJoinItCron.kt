package it.workQuest.cron.justJoinIt

import it.workQuest.cron.JobOfferRequestCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class JustJoinItCount(val count: Int)

@Component
class JustJoinItCron(
    jobOfferCountService: JobOfferCountService,
    urlBuilder: JustJoinItRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountService, urlBuilder, JobPortal.JUST_JOIN_IT) {

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        val justJoinItCount = Json.decodeFromString<JustJoinItCount>(responseData)

        return justJoinItCount.count
    }
}