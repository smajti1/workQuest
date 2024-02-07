package it.workQuest.cron.inHireIo

import it.workQuest.cron.JobOfferRequestCronAbstract
import it.workQuest.model.JobPortal
import it.workQuest.service.JobOfferCountService
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
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
class InHireIoCron(
    jobOfferCountService: JobOfferCountService,
    urlBuilder: InHireIoRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountService, urlBuilder, JobPortal.IN_HIRE_IO) {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun getOffersNumber(sleepTime: Long) {
        createRequestsForJobPortalAndCategoryByCities(sleepTime)
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return jsonFormat.decodeFromString<InHireIoResponse>(responseData).response.count()
    }
}