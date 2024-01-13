package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import it.dziubinski.workInIt.cron.itLeaders.ItLeadersRequestBuilder
import it.dziubinski.workInIt.model.JobCategory

class ItLeadersRequestBuilderUnitTest(
    private val itLeadersRequestBuilder: ItLeadersRequestBuilder = ItLeadersRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        itLeadersRequestBuilder.build().url.toString() shouldBe "https://app.it-leaders.pl/pl/api_v1/getRecrutations"
    }

    "builder should contain application/infiniteSearch+json header" {
        itLeadersRequestBuilder.build().headers.toString() shouldBe "{Content-Type=[application/json]}"
    }

    "body should contain city name Warszawa" {
        String(itLeadersRequestBuilder.apply { city = "Warsaw" }
            .build().body.toByteArray()) shouldBe """{"filter":{"city":"Warszawa","name":""},"page":1}"""
    }

    "url fo city Warsaw and kotlin" {
        String(itLeadersRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Kotlin }
            .build().body.toByteArray()) shouldBe """{"filter":{"city":"Warszawa","name":"Kotlin"},"page":1}"""
    }

    "url fo unknown city PHP" {
        String(itLeadersRequestBuilder.apply { city = "Łomianki"; jobCategory = JobCategory.Php }
            .build().body.toByteArray()) shouldBe """{"filter":{"city":"","name":"PHP"},"page":1}"""
    }
})