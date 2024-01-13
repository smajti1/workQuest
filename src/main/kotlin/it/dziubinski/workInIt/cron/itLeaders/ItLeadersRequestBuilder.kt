package it.dziubinski.workInIt.cron.itLeaders

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.cron.RequestBuilderInterface
import it.dziubinski.workInIt.model.JobCategory
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

const val IT_LEADERS = "https://app.it-leaders.pl/pl/api_v1/getRecrutations"

@Serializable
data class ItLeadersRequest(
    val filter: ItLeadersFilterRequest,
    val page: Int = 1,
) {
    @Serializable
    data class ItLeadersFilterRequest(
        val city: String = "",
        val name: String = "",
    )
}

@Component
class ItLeadersRequestBuilder : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    private val jsonFormat = Json { encodeDefaults = true }

    override fun build(): Request {
        val request = Fuel.post(IT_LEADERS)
            .header(Headers.CONTENT_TYPE, "application/json")
        val skill = when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> "Kotlin"
            JobCategory.Php -> "PHP"
        }
        val city = when (this.city) {
            "Warsaw" -> "Warszawa"
            else -> ""
        }
        val itLeadersFilterRequest = ItLeadersRequest.ItLeadersFilterRequest(city, skill)
        val value = ItLeadersRequest(itLeadersFilterRequest)
        return request.body(
            jsonFormat.encodeToString(value)
        )
    }
}