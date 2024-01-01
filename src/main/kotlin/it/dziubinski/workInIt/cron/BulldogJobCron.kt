package it.dziubinski.workInIt.cron

import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
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
@EnableScheduling
class BulldogJobCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: BulldogJobRequestBuilder,
) : JobOfferCronAbstract(jobOfferCountRepository, urlBuilder) {

    @Scheduled(cron = "0 3 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:03:00
    fun getTotalOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.BULLDOG_JOB, JobCategory.Total)
    }

    @Scheduled(cron = "1 3 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:03:01
    fun getKotlinOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.BULLDOG_JOB, JobCategory.Kotlin)
    }

    @Scheduled(cron = "2 3 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:03:02
    fun getPhpOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.BULLDOG_JOB, JobCategory.Php)
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber, ::getKotlinOffersNumber, ::getPhpOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return Json.decodeFromString<BulldogJobCronResponse>(responseData).data.searchJobs.totalCount
    }

}