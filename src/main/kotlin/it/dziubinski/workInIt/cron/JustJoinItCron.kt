package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.httpGet
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

const val JUST_JOIN_API_URL = "https://api.justjoin.it/v2"

@Serializable
data class JustJoinItCount(val count: Int)

@Component
@EnableScheduling
class JustJoinItCron(private val jobOfferCountRepository: JobOfferCountRepository) {

    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00
    fun getOffersNumber() {
        val url = "$JUST_JOIN_API_URL/user-panel/offers/count?city=Warszawa&withSalary=false&salaryCurrencies=PLN"
        println(url)
        url.httpGet().responseString { request, response, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value
                    println(data)
                    val justJoinItCount = Json.decodeFromString<JustJoinItCount>(data)
                    val newJobOfferCount = saveNewJobOfferCount(justJoinItCount.count)
                    println("added new offer: " + newJobOfferCount.id)
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
        category: JobCategory = JobCategory.Total,
        city: String? = null
    ): JobOfferCount {
        val jobOfferCount = JobOfferCount(JobPortal.JUST_JOIN_IT, offerCount, category, city)
        jobOfferCountRepository.save(jobOfferCount)
        return jobOfferCount
    }
}