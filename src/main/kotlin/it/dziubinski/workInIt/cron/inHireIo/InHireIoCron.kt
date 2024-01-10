package it.dziubinski.workInIt.cron.inHireIo

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
data class InHireIoResponse(
    val result: String,
    val response: List<InHireIoResponse>,
) {
    @Serializable
    data class InHireIoResponse(
        val process_title: String,
        val company_name: String,
        val recruitment_process_id: Int,
    )
}

@Component
@EnableScheduling
class InHireIoCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: InHireIoRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    @Scheduled(cron = "0 4 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:04:00
    fun getTotalOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.IN_HIRE_IO, JobCategory.Total)
    }

    @Scheduled(cron = "1 4 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:04:01
    fun getKotlinOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.IN_HIRE_IO, JobCategory.Kotlin)
    }

    @Scheduled(cron = "2 4 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:04:02
    fun getPhpOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.IN_HIRE_IO, JobCategory.Php)
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber, ::getKotlinOffersNumber, ::getPhpOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<InHireIoResponse>(responseData).response.count()
    }
}