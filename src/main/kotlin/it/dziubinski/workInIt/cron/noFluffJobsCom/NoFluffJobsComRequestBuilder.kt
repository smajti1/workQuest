package it.dziubinski.workInIt.cron.noFluffJobsCom

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.cron.RequestBuilderInterface
import it.dziubinski.workInIt.model.JobCategory
import org.springframework.stereotype.Component

const val NO_FLUFF_JOBS_API_URL = "https://nofluffjobs.com/api/search/posting"

@Component
class NoFluffJobsComRequestBuilder() : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        val request = Fuel.post("$NO_FLUFF_JOBS_API_URL?pageSize=0&salaryCurrency=PLN&salaryPeriod=month&region=pl")
            .header(Headers.CONTENT_TYPE, "application/infiniteSearch+json")
        var rawSearch = when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> "requirement=Kotlin"
            JobCategory.Php -> "requirement=PHP"
        }
        rawSearch += when (this.city) {
            "Warsaw" -> " city=Warszawa"
            else -> ""
        }
        return request.body("{\"rawSearch\":\"${rawSearch}\",\"pageSize\":0}")
    }
}