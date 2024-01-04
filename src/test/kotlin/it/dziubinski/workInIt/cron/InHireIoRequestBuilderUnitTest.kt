package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.dziubinski.workInIt.model.JobCategory

class InHireIoRequestBuilderUnitTest(
    private val inHireIoRequestBuilder: InHireIoRequestBuilder = InHireIoRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        inHireIoRequestBuilder.build().url.toString() shouldBe "https://inhire.io/getAllOffersNonAuth"
    }

    "request body should contain city name Warszawa" {
        String(inHireIoRequestBuilder.apply { city = "Warsaw" }.build().body.toByteArray()) shouldContain "\"city_ids\":[2]"
    }

    "request body fo kotlin" {
        String(inHireIoRequestBuilder.apply { jobCategory = JobCategory.Kotlin }.build().body.toByteArray()) shouldContain "\"it_technology_ids\":[413]"
    }

    "request body for PHP" {
        String(inHireIoRequestBuilder.apply { jobCategory = JobCategory.Php }.build().body.toByteArray()) shouldContain "\"it_technology_ids\":[4]"
    }

    "request body for job category and city" {
        String(inHireIoRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Php }.build().body.toByteArray()) shouldBe
                "{\"city_ids\":[2],\"it_technology_ids\":[4],\"include_undisclosed\":true}"
    }
})