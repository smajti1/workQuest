package it.workQuest.cron.bulldogJob

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import it.workQuest.cron.RequestBuilderInterface
import it.workQuest.model.JobCategory
import org.springframework.stereotype.Component

const val BULLDOG_JOB_API_URL = "https://bulldogjob.pl/graphql"

@Component
class BulldogJobRequestBuilder() : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        val request = Fuel.post(BULLDOG_JOB_API_URL)
            .header(Headers.CONTENT_TYPE, "application/json")
            .header(Headers.ACCEPT, "application/json")
        val skill = when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> "\\\"Kotlin\\\""
            JobCategory.Php -> "\\\"PHP\\\""
        }
        val city = when (this.city) {
            "Warsaw" -> "\\\"Warszawa\\\""
            else -> ""
        }
        return request.body("""{"query":"query searchJobs { searchJobs(filters: { skills: [$skill], city: [$city]}) { totalCount } }","operationName":"searchJobs"}""")
    }
}