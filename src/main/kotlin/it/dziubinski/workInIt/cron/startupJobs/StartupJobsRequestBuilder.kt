package it.dziubinski.workInIt.cron.startupJobs

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.cron.RequestBuilderInterface
import it.dziubinski.workInIt.model.JobCategory
import org.springframework.stereotype.Component

const val STARTUP_JOBS_API_URL = "https://www.startupjobs.com/api/offers?area[]=development"

@Component
class StartupJobsRequestBuilder : RequestBuilderInterface {
    override var city: String? = null
    override var jobCategory: JobCategory = JobCategory.Total

    override fun build(): Request {
        var url = STARTUP_JOBS_API_URL
        url += when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> "&technological-tag[]=kotlin"
            JobCategory.Php -> "&technological-tag[]=php"
        }
        url += when (this.city) {
            "Warsaw" -> "&location[]=ChIJAZ-GmmbMHkcR_NPqiCq-8HI"
            else -> ""
        }
        return Fuel.get(url)
    }
}