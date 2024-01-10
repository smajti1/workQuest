package it.dziubinski.workInIt.cron.startupJobs

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
data class StartupJobsCronRequest(
    val resultCount: Int,
)

@Component
@EnableScheduling
class StartupJobsCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: StartupJobsRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    @Scheduled(cron = "0 8 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:08:00
    fun getOffersNumber() {
        for (jobCategory in JobCategory.entries) {
            createRequestsForJobPortalAndCategoryByCities(JobPortal.STARTUP_JOBS, jobCategory)
            Thread.sleep(1_000)
        }
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<StartupJobsCronRequest>(responseData).resultCount
    }
}