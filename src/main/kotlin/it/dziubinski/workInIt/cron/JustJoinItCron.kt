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
data class JustJoinItCount(val count: Int)

@Component
@EnableScheduling
class JustJoinItCron(
    private val jobOfferCountRepository: JobOfferCountRepository,
    private val urlBuilder: JustJoinItRequestBuilder,
) {

    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:00
    fun getTotalOffersNumber() {
        val jobCategory = JobCategory.Total
        val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; city = null }.build()
        sendResponseAndCreateJobOfferCountEntity(urlTotal, jobCategory)
        val city = "Warszawa"
        val urlWarsaw = urlBuilder.apply { this.city = city }.build()
        sendResponseAndCreateJobOfferCountEntity(urlWarsaw, jobCategory, city)
    }

    @Scheduled(cron = "1 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:01
    fun getKotlinOffersNumber() {
        val jobCategory = JobCategory.Kotlin
        val urlTotal = urlBuilder.apply { this.jobCategory = jobCategory; city = null }.build()
        sendResponseAndCreateJobOfferCountEntity(urlTotal, jobCategory)
        val city = "Warszawa"
        val urlWarsaw = urlBuilder.apply { this.city = city }.build()
        sendResponseAndCreateJobOfferCountEntity(urlWarsaw, jobCategory, city)
    }

    @Scheduled(cron = "2 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:02
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
                    val justJoinItCount = Json.decodeFromString<JustJoinItCount>(data)
                    saveNewJobOfferCount(justJoinItCount.count, jobCategory, city)
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
        val jobOfferCount = JobOfferCount(JobPortal.JUST_JOIN_IT, offerCount, category, city)
        jobOfferCountRepository.save(jobOfferCount)
        return jobOfferCount
    }

    fun getCronFunctionArray(): Array<() -> Unit> {
        return arrayOf(::getTotalOffersNumber, ::getKotlinOffersNumber, ::getPhpOffersNumber)
    }
}