package it.workQuest.cron.indeedCom

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import it.workQuest.cron.RequestBuilderInterface
import it.workQuest.model.JobCategory
import org.springframework.stereotype.Component

const val INDEED_COM_URL = "https://pl.indeed.com/jobs?radius=0"

@Component
class IndeedComRequestBuilder() : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        var url = INDEED_COM_URL
        url += "&q=" + when (this.jobCategory) {
            JobCategory.Total -> "developer"
            JobCategory.Kotlin -> "kotlin"
            JobCategory.Php -> "php"
        }
        url += when (this.city) {
            "Warsaw" -> "&l=Warszawa"
            else -> ""
        }
        return Fuel.get(url)
    }
}