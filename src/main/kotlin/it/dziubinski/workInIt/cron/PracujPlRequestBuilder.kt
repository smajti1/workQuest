package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.model.JobCategory
import org.springframework.stereotype.Component

const val PRACUJ_PL_API_URL = "https://massachusetts.pracuj.pl/jobOffers/listing/count"

@Component
class PracujPlRequestBuilder : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        var url = "$PRACUJ_PL_API_URL?pn=1&iwhpl=true&subservice=1"

        url += when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> "&itth=43"
            JobCategory.Php -> "&itth=40"
        }
        url += when (this.city) {
            "Warsaw" -> "&wp=Warszawa"
            else -> ""
        }

        return Fuel.get(url).header(Headers.CONTENT_TYPE, "application/json")
    }
}