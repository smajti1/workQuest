package it.dziubinski.workInIt.cron.solidJobs

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.result.Result
import it.dziubinski.workInIt.cron.JobOfferCronInterface
import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.service.JobOfferCountService
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
    private val jobOfferCountService: JobOfferCountService,
) : JobOfferCronInterface {

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    fun getOffersNumber(sleepTime: Long) {
        val jobPortalCitiesByCategory = jobOfferCountService.getJobPortalAlreadyCreatedCitiesByCategoryMap(JobPortal.SOLID_JOBS)
        if (jobPortalCitiesByCategory.count() >= JobOfferCount.cities.count() + JobPortal.entries.count()) {
            return
        }
        val request = Fuel.get(SOLID_JOBS_API_URL)
            .header(Headers.CONTENT_TYPE, "application/vnd.solidjobs.jobofferlist+json; charset=UTF-8")
            .header(Headers.ACCEPT, "application/vnd.solidjobs.jobofferlist+json, application/json, text/plain, */*")
        println(request.cUrlString())

        request.responseString { _, _, result ->
            when (result) {
                is Result.Success -> {
                    val data = result.value
                    extractDataFromRequestAndSaveOfferNumber(data, jobPortalCitiesByCategory)
                }

                is Result.Failure -> {
                    val ex = result.error.exception
                    println("Error: $ex")
                }
            }
        }
    }

    private fun extractDataFromRequestAndSaveOfferNumber(responseData: String, jobPortalCitiesByCategory: Map<JobCategory, List<String?>>) {
        val solidJobsOffers = jsonFormat.decodeFromString<List<SolidJobs>>(responseData)
        val city = "Warsaw"
        val cityToSearchInSolidJobsRequest = "Warszawa"

        if (!jobPortalCitiesByCategory.any { it.key == JobCategory.Total && it.value.contains(null) }) {
            val jobOfferCountTotal = solidJobsOffers.count()
            jobOfferCountService.saveNewJobOfferCount(JobPortal.SOLID_JOBS, JobCategory.Total, city = null, jobOfferCountTotal)
        }
        if (!jobPortalCitiesByCategory.any { it.key == JobCategory.Total && it.value.contains(city) }) {
            val jobOfferCountTotalWarsaw = solidJobsOffers.count { it.companyCity == cityToSearchInSolidJobsRequest }
            jobOfferCountService.saveNewJobOfferCount(JobPortal.SOLID_JOBS, JobCategory.Total, city, jobOfferCountTotalWarsaw)
        }

        val jobOfferCountTotalKotlin =
            solidJobsOffers.filter { it1 -> it1.requiredSkills.any { it.name.contains(JobCategory.Kotlin.toString(), ignoreCase = true) } }
        if (!jobPortalCitiesByCategory.any { it.key == JobCategory.Kotlin && it.value.contains(null) }) {
            jobOfferCountService.saveNewJobOfferCount(JobPortal.SOLID_JOBS, JobCategory.Kotlin, city = null, jobOfferCountTotalKotlin.count())
        }
        if (!jobPortalCitiesByCategory.any { it.key == JobCategory.Kotlin && it.value.contains(city) }) {
            val jobOfferCountTotalKotlinWarsaw = jobOfferCountTotalKotlin.count { it.companyCity == cityToSearchInSolidJobsRequest }
            jobOfferCountService.saveNewJobOfferCount(JobPortal.SOLID_JOBS, JobCategory.Kotlin, city, jobOfferCountTotalKotlinWarsaw)
        }

        val jobOfferCountTotalPhp =
            solidJobsOffers.filter { it.subCategory.contains(JobCategory.Php.toString(), ignoreCase = true) }
        if (!jobPortalCitiesByCategory.any { it.key == JobCategory.Php && it.value.contains(null) }) {
            jobOfferCountService.saveNewJobOfferCount(JobPortal.SOLID_JOBS, JobCategory.Php, city = null, jobOfferCountTotalPhp.count())
        }

        if (!jobPortalCitiesByCategory.any { it.key == JobCategory.Php && it.value.contains(city) }) {
            val jobOfferCountTotalPhpWarsaw = jobOfferCountTotalPhp.count { it.companyCity == cityToSearchInSolidJobsRequest }
            jobOfferCountService.saveNewJobOfferCount(JobPortal.SOLID_JOBS, JobCategory.Php, city, jobOfferCountTotalPhpWarsaw)
        }
    }

    override fun getCronFunctionArray(): Array<(Long) -> Unit> {
        return arrayOf(::getOffersNumber)
    }
}