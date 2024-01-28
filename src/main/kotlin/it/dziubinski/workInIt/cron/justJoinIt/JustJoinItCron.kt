package it.dziubinski.workInIt.cron.justJoinIt

import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class JustJoinItCount(val count: Int)

@Component
class JustJoinItCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: JustJoinItRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder, JobPortal.JUST_JOIN_IT) {

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