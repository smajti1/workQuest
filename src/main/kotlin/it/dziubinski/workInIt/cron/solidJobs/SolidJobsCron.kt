package it.dziubinski.workInIt.cron.solidJobs

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.result.Result
import it.dziubinski.workInIt.cron.JobOfferCronInterface
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Serializable
data class SolidJobs(
    val id: Int,
    val jobOfferKey: String,
    val companyCity: String,
    val subCategory: String,
    val requiredSkills: List<SolidJobsSkill>,
) {
    @Serializable
    data class SolidJobsSkill(
        val name: String,
        val skillType: String,
        val skillLevel: String,
    )
}

const val SOLID_JOBS_API_URL = "https://solid.jobs/api/offers?division=it&sortOrder=default"

@Component
class SolidJobsCron(
    val jobOfferCountRepository: JobOfferCountRepository,
) : JobOfferCronInterface {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun getOffersNumber(sleepTime: Long) {
        val request = Fuel.get(SOLID_JOBS_API_URL)
            .header(Headers.CONTENT_TYPE, "application/vnd.solidjobs.jobofferlist+json; charset=UTF-8")
            .header(Headers.ACCEPT, "application/vnd.solidjobs.jobofferlist+json, application/json, text/plain, */*")
        println(request.cUrlString())

        request.responseString { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value
                    extracted(data)
                }

                is Result.Failure -> {
                    val ex = result.error.exception
                    println("Error: $ex")
                }
            }
        }
    }

    private fun extracted(responseData: String) {
        val solidJobsOffers = jsonFormat.decodeFromString<List<SolidJobs>>(responseData)
        val jobOfferCountTotal = solidJobsOffers.count()
        saveNewJobOfferCount(JobCategory.Total, city = null, jobOfferCountTotal)

        val city = "Warsaw"
        val cityToSearchInSolidJobsRequest = "Warszawa"
        val jobOfferCountTotalWarsaw = solidJobsOffers.count { it.companyCity == cityToSearchInSolidJobsRequest }
        saveNewJobOfferCount(JobCategory.Total, city, jobOfferCountTotalWarsaw)

        val jobOfferCountTotalKotlin =
            solidJobsOffers.filter { it1 -> it1.requiredSkills.any { it.name.contains(JobCategory.Kotlin.toString(), ignoreCase = true) } }
        saveNewJobOfferCount(JobCategory.Kotlin, city = null, jobOfferCountTotalKotlin.count())

        val jobOfferCountTotalKotlinWarsaw = jobOfferCountTotalKotlin.count { it.companyCity == cityToSearchInSolidJobsRequest }
        saveNewJobOfferCount(JobCategory.Kotlin, city, jobOfferCountTotalKotlinWarsaw)

        val jobOfferCountTotalPhp =
            solidJobsOffers.filter { it.subCategory.contains(JobCategory.Php.toString(), ignoreCase = true) }
        saveNewJobOfferCount(JobCategory.Php, city = null, jobOfferCountTotalPhp.count())

        val jobOfferCountTotalPhpWarsaw = jobOfferCountTotalPhp.count { it.companyCity == cityToSearchInSolidJobsRequest }
        saveNewJobOfferCount(JobCategory.Php, city, jobOfferCountTotalPhpWarsaw)
    }

    private fun saveNewJobOfferCount(
        category: JobCategory,
        city: String?,
        offerCount: Int,
    ): JobOfferCount {
        val jobOfferCount = JobOfferCount(JobPortal.SOLID_JOBS, offerCount, category, city)
        jobOfferCountRepository.save(jobOfferCount)

        return jobOfferCount
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }
}