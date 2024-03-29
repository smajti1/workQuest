package it.workQuest.cron.bulldogJob

import it.workQuest.cron.JobOfferRequestCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class BulldogJobCronResponse(
    val data: BulldogJobCronResponseData,
) {
    @Serializable
    data class BulldogJobCronResponseData(
        val searchJobs: BulldogJobCronResponseDataSearchJob,
    ) {
        @Serializable
        data class BulldogJobCronResponseDataSearchJob(val totalCount: Int)
    }
}

@Component
class BulldogJobCron(
    jobOfferCountService: JobOfferCountService,
    urlBuilder: BulldogJobRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountService, urlBuilder, JobPortal.BULLDOG_JOB) {

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return Json.decodeFromString<BulldogJobCronResponse>(responseData).data.searchJobs.totalCount
    }

}