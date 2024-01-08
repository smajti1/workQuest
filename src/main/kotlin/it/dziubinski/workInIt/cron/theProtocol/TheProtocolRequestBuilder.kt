package it.dziubinski.workInIt.cron.theProtocol

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.cron.RequestBuilderInterface
import it.dziubinski.workInIt.model.JobCategory
import org.springframework.stereotype.Component

const val THE_PROTOCOL_URL = "https://theprotocol.it"

@Component
class TheProtocolRequestBuilder() : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        var url = "$THE_PROTOCOL_URL/filtry"
        if (this.jobCategory == JobCategory.Total && this.city.isNullOrEmpty()) {
            url += "/zdalna,hybrydowa,stacjonarna;rw"
        } else {
            url += when (this.jobCategory) {
                JobCategory.Total -> ""
                JobCategory.Kotlin -> "/kotlin;t"
                JobCategory.Php -> "/php;t"
            }
            url += when (this.city) {
                "Warsaw" -> "/warszawa;wp"
                else -> ""
            }
        }
        return Fuel.get(url)
    }
}