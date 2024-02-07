package it.workQuest.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.workQuest.cron.bulldogJob.BulldogJobRequestBuilder
import it.workQuest.model.JobCategory

class BulldogJobRequestBuilderUnitTest(
    private val bulldogJobRequestBuilder: BulldogJobRequestBuilder = BulldogJobRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        bulldogJobRequestBuilder.build().url.toString() shouldBe "https://bulldogjob.pl/graphql"
    }

    "url should contain city name Warszawa" {
        String(bulldogJobRequestBuilder.apply { city = "Warsaw" }.build().body.toByteArray()) shouldContain "Warszawa"
    }

    "request body for kotlin" {
        String(bulldogJobRequestBuilder.apply { jobCategory = JobCategory.Kotlin }.build().body.toByteArray()) shouldContain "Kotlin"
    }

    "request body for PHP" {
        String(bulldogJobRequestBuilder.apply { jobCategory = JobCategory.Php }.build().body.toByteArray()) shouldContain "PHP"
    }

    "request body for job category and city" {
        String(bulldogJobRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Php }.build().body.toByteArray()) shouldBe
                "{\"query\":\"query searchJobs { searchJobs(filters: { skills: [\\\"PHP\\\"], city: [\\\"Warszawa\\\"]}) { totalCount } }\",\"operationName\":\"searchJobs\"}"
    }
})