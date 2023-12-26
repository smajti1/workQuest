package it.dziubinski.workInIt.cron

import com.github.kittinunf.fuel.core.Request
import it.dziubinski.workInIt.model.JobCategory

interface RequestBuilderInterface {
    var city: String?
    var jobCategory: JobCategory

    fun build(): Request
}