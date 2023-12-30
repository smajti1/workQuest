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
data class JustJoinItCount(val count: Int)

@Component
@EnableScheduling
class JustJoinItCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: JustJoinItRequestBuilder,
) : JobOfferCronAbstract(jobOfferCountRepository, urlBuilder) {

    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:00
    fun getTotalOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.JUST_JOIN_IT, JobCategory.Total)
    }

    @Scheduled(cron = "1 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:01
    fun getKotlinOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.JUST_JOIN_IT, JobCategory.Kotlin)
    }

    @Scheduled(cron = "2 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:02
    fun getPhpOffersNumber() {
        createRequestsForJobPortalAndCategoryByCities(JobPortal.JUST_JOIN_IT, JobCategory.Php)
    }

    override fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber, ::getKotlinOffersNumber, ::getPhpOffersNumber)
    }

    override fun getCountFromRequest(responseData: String): Int {
        val justJoinItCount = Json.decodeFromString<JustJoinItCount>(responseData)

        return justJoinItCount.count;
    }
}