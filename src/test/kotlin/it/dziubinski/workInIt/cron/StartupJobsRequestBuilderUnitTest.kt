package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.dziubinski.workInIt.cron.startupJobs.StartupJobsRequestBuilder
import it.dziubinski.workInIt.model.JobCategory

class StartupJobsRequestBuilderUnitTest(
    private val startupJobsRequestBuilder: StartupJobsRequestBuilder = StartupJobsRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        startupJobsRequestBuilder.build().url.toString() shouldBe "https://www.startupjobs.com/api/offers?area[]=development"
    }

    "url should contain city name Warszawa" {
        startupJobsRequestBuilder.apply { city = "Warsaw" }.build().url.toString() shouldContain "&location[]=ChIJAZ-GmmbMHkcR_NPqiCq-8HI"
    }

    "request body for kotlin" {
        startupJobsRequestBuilder.apply { jobCategory = JobCategory.Kotlin }.build().url.toString() shouldContain "kotlin"
    }

    "request body for PHP" {
        startupJobsRequestBuilder.apply { jobCategory = JobCategory.Php }.build().url.toString() shouldContain "php"
    }

    "request body for job category and city" {
        startupJobsRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Php }.build().url.toString() shouldBe
                "https://www.startupjobs.com/api/offers?area[]=development&technological-tag[]=php&location[]=ChIJAZ-GmmbMHkcR_NPqiCq-8HI"
    }
})