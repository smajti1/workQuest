package it.dziubinski.workInIt.cron.pracujPl

import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class PracujPlResponse(
    val offersCount: Int,
    val jobCenterOffersCount: Int,
)

@Component
class PracujPlCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: PracujPlRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder, JobPortal.PRACUJ_PL) {

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return Json.decodeFromString<PracujPlResponse>(responseData).offersCount
    }
}