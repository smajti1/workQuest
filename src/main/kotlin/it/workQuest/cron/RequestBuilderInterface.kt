package it.workQuest.cron

import com.github.kittinunf.fuel.core.Request
import it.workQuest.model.JobCategory

interface RequestBuilderInterface {
    var city: String?
    var jobCategory: JobCategory

    fun build(): Request
}