package it.dziubinski.workInIt.cron

import it.dziubinski.workInIt.model.JobCategory
import org.springframework.stereotype.Component

const val JUST_JOIN_API_URL = "https://api.justjoin.it/v2/user-panel/offers/count"

@Component
class JustJoinItUrlBuilder() {
    var city: String? = null
    var jobCategory: JobCategory = JobCategory.Total

    override fun toString(): String {
        var url = "${JUST_JOIN_API_URL}?withSalary=false&salaryCurrencies=PLN";
        if (this.city !== null && this.city!!.isNotEmpty()) {
            url += "&city=${this.city}"
        }
        url += when (this.jobCategory) {
            JobCategory.Total -> ""
            JobCategory.Kotlin -> "&keywords[]=kotlin"
            JobCategory.Php -> "&categories[]=3"
        }
        return url
    }
}