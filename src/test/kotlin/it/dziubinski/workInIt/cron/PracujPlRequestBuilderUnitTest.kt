package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.dziubinski.workInIt.cron.pracujPl.PracujPlRequestBuilder
import it.dziubinski.workInIt.model.JobCategory

class PracujPlRequestBuilderUnitTest(
    private val pracujPlRequestBuilder: PracujPlRequestBuilder = PracujPlRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        pracujPlRequestBuilder.build().url.toString() shouldBe "https://massachusetts.pracuj.pl/jobOffers/listing/count?pn=1&iwhpl=true&subservice=1"
    }

    "url should contain city name Warszawa" {
        pracujPlRequestBuilder.apply { city = "Warsaw" }.build().url.toString() shouldContain "Warszawa"
    }

    "url fo kotlin" {
        pracujPlRequestBuilder.apply { jobCategory = JobCategory.Kotlin }.build().url.toString() shouldContain "&itth=43"
    }

    "url for PHP" {
        pracujPlRequestBuilder.apply { jobCategory = JobCategory.Php }.build().url.toString() shouldContain "&itth=40"
    }

    "url for job category and city" {
        pracujPlRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Php }.build().url.toString() shouldBe
                "https://massachusetts.pracuj.pl/jobOffers/listing/count?pn=1&iwhpl=true&subservice=1&itth=40&wp=Warszawa"
    }
})