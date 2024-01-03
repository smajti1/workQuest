package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.model.JobCategory
import org.springframework.stereotype.Component

const val IN_HIRE_IO = "https://inhire.io/getAllOffersNonAuth"

@Component
class InHireIoRequestBuilder : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        val request = Fuel.post(IN_HIRE_IO)
            .header(Headers.CONTENT_TYPE, "application/json")
            .header(Headers.ACCEPT, "application/json")
        val skill = when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> 413
            JobCategory.Php -> 4
        }
        val city = when (this.city) {
            "Warszawa" -> 2
            else -> ""
        }
        return request.body("{\"city_ids\":[$city],\"it_technology_ids\":[$skill],\"include_undisclosed\":true}")
    }
}