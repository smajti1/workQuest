package it.dziubinski.workInIt.cron.noFluffJobsCom

import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Serializable
data class NoFluffJobsComCount(
    val totalCount: Int,
    val totalPages: Int,
)

@Component
@EnableScheduling
class NoFluffJobsComCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: NoFluffJobsComRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    @Scheduled(cron = "0 1 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:01:00
    fun getTotalOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.NO_FLUFF_JOBS_COM, JobCategory.Total)
    }

    @Scheduled(cron = "1 1 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:01:01
    fun getKotlinOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.NO_FLUFF_JOBS_COM, JobCategory.Kotlin)
    }

    @Scheduled(cron = "2 1 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:01:02
    fun getPhpOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.NO_FLUFF_JOBS_COM, JobCategory.Php)
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber, ::getKotlinOffersNumber, ::getPhpOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<NoFluffJobsComCount>(responseData).totalCount
    }
}