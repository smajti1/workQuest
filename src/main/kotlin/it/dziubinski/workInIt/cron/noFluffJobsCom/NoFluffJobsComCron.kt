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
    val criteriaSearch: CriteriaSearch,
    val postings: List<String>,
    val totalCount: Int,
    val totalPages: Int,
    val exactMatchesPages: Int,
    val rawSearch: String,
    val locationCriteria: Boolean,
    val divs: Int,
    val additionalSearch: List<String>,
) {
    @Serializable
    data class CriteriaSearch(
        val country: List<String>,
        val city: List<String>,
        val more: List<String>,
        val employment: List<String>,
        val requirement: List<String>,
        val salary: List<String>,
        val jobPosition: List<String>,
        val province: List<String>,
        val company: List<String>,
        val id: List<String>,
        val category: List<String>,
        val keyword: List<String>,
        val jobLanguage: List<String>,
        val seniority: List<String>,
    )
}

@Component
@EnableScheduling
class NoFluffJobsComCron(
    jobOfferCountRepository: JobOfferCountRepository,
    urlBuilder: NoFluffJobsComRequestBuilder,
) : JobOfferRequestCronAbstract(jobOfferCountRepository, urlBuilder) {

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
        val noFluffJobsComCount = Json.decodeFromString<NoFluffJobsComCount>(responseData)

        return noFluffJobsComCount.totalCount
    }
}