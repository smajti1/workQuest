package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.result.Result
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
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
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val urlBuilder: NoFluffJobsComRequestBuilder,
) {

    // @Scheduled(cron = "*/60 * * * * ?", zone = "Europe/Warsaw")
    @Scheduled(cron = "0 1 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:01:00
    fun getTotalOffersNumber() {
        val jobCategory = JobCategory.Total
        val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; city = null }.build()
        sendResponseAndCreateJobOfferCountEntity(urlTotal, jobCategory)
        val city = "Warszawa"
        val urlWarsaw = urlBuilder.apply { this.city = city }.build()
        sendResponseAndCreateJobOfferCountEntity(urlWarsaw, jobCategory, city)
    }

    //    @Scheduled(cron = "*/60 * * * * ?", zone = "Europe/Warsaw")
    @Scheduled(cron = "1 1 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:01:01
    fun getKotlinOffersNumber() {
        val jobCategory = JobCategory.Kotlin
        val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; city = null }.build()
        sendResponseAndCreateJobOfferCountEntity(urlTotal, jobCategory)
        val city = "Warszawa"
        val urlWarsaw = urlBuilder.apply { this.city = city }.build()
        sendResponseAndCreateJobOfferCountEntity(urlWarsaw, jobCategory, city)
    }

    //    @Scheduled(cron = "*/60 * * * * ?", zone = "Europe/Warsaw")
    @Scheduled(cron = "2 1 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:01:02
    fun getPhpOffersNumber() {
        val jobCategory = JobCategory.Php
        val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; city = null }.build()
        sendResponseAndCreateJobOfferCountEntity(urlTotal, jobCategory)
        val city = "Warszawa"
        val urlWarsaw = urlBuilder.apply { this.city = city }.build()
        sendResponseAndCreateJobOfferCountEntity(urlWarsaw, jobCategory, city)
    }

    private fun sendResponseAndCreateJobOfferCountEntity(
        request: Request,
        jobCategory: JobCategory,
        city: String? = null,
    ) {
        println(request.url)
        request.responseString { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value
                    val noFluffJobsComCount = Json.decodeFromString<NoFluffJobsComCount>(data)
                    saveNewJobOfferCount(noFluffJobsComCount.totalCount, jobCategory, city)
                }

                is Result.Failure -> {
                    val ex = result.error.exception
                    println("Error: $ex")
                }
            }
        }
    }

    private fun saveNewJobOfferCount(
        offerCount: Int,
        category: JobCategory,
        city: String? = null,
    ): JobOfferCount {
        val jobOfferCount = JobOfferCount(JobPortal.NO_FLUFF_JOBS_COM, offerCount, category, city)
        jobOfferCountRepository.save(jobOfferCount)
        return jobOfferCount
    }
}