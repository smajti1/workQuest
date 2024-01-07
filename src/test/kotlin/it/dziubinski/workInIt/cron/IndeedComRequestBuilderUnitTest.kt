package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.dziubinski.workInIt.model.JobCategory

class IndeedComRequestBuilderUnitTest(
    private val indeedComRequestBuilder: IndeedComRequestBuilder = IndeedComRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        indeedComRequestBuilder.build().url.toString() shouldBe "https://pl.indeed.com/jobs?radius=0&q=developer"
    }

    "url should contain city name Warszawa" {
        indeedComRequestBuilder.apply { city = "Warsaw" }.build().url.toString() shouldContain "Warszawa"
    }

    "request body for kotlin" {
        indeedComRequestBuilder.apply { jobCategory = JobCategory.Kotlin }.build().url.toString() shouldContain "kotlin"
    }

    "request body for PHP" {
        indeedComRequestBuilder.apply { jobCategory = JobCategory.Php }.build().url.toString() shouldContain "php"
    }

    "request body for job category and city" {
        indeedComRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Php }.build().url.toString() shouldBe
                "https://pl.indeed.com/jobs?radius=0&q=php&l=Warszawa"
    }
})