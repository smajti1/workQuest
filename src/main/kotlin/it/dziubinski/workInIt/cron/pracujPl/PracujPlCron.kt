package it.dziubinski.workInIt.cron.pracujPl

import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.scheduling.annotation.Scheduled
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
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder) {

    @Scheduled(cron = "0 5 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:05:00
    fun getTotalOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.PRACUJ_PL, JobCategory.Total)
    }

    @Scheduled(cron = "1 5 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:05:01
    fun getKotlinOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.PRACUJ_PL, JobCategory.Kotlin)
    }

    @Scheduled(cron = "2 5 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:05:02
    fun getPhpOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.PRACUJ_PL, JobCategory.Php)
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber, ::getKotlinOffersNumber, ::getPhpOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        return Json.decodeFromString<PracujPlResponse>(responseData).offersCount
    }
}