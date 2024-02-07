package it.workQuest.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.workQuest.cron.theProtocol.TheProtocolRequestBuilder
import it.workQuest.model.JobCategory

class TheProtocolRequestBuilderUnitTest(
    private val theProtocolRequestBuilder: TheProtocolRequestBuilder = TheProtocolRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        theProtocolRequestBuilder.build().url.toString() shouldBe "https://theprotocol.it/filtry/zdalna,hybrydowa,stacjonarna;rw"
    }

    "url should contain city name Warszawa" {
        theProtocolRequestBuilder.apply { city = "Warsaw" }.build().url.toString() shouldContain "warszawa"
    }

    "request body for kotlin" {
        theProtocolRequestBuilder.apply { jobCategory = JobCategory.Kotlin }.build().url.toString() shouldContain "kotlin"
    }

    "request body for PHP" {
        theProtocolRequestBuilder.apply { jobCategory = JobCategory.Php }.build().url.toString() shouldContain "php"
    }

    "request body for job category and city" {
        theProtocolRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Php }.build().url.toString() shouldBe
                "https://theprotocol.it/filtry/php;t/warszawa;wp"
    }
})
