package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.dziubinski.workInIt.model.JobCategory

class JustJoinItRequestBuilderUnitTest(
    private val justJoinItRequestBuilder: JustJoinItRequestBuilder = JustJoinItRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        justJoinItRequestBuilder.build().url.toString() shouldBe "https://api.justjoin.it/v2/user-panel/offers/count?withSalary=false&salaryCurrencies=PLN"
    }

    "url should contain city name Warszawa" {
        justJoinItRequestBuilder.apply { city = "Warsaw" }.build().url.toString() shouldContain "Warszawa"
    }

    "url fo kotlin" {
        justJoinItRequestBuilder.apply { jobCategory = JobCategory.Kotlin }
            .build().url.toString() shouldContain "&keywords[]=kotlin"
    }

    "url for PHP" {
        justJoinItRequestBuilder.apply { jobCategory = JobCategory.Php }
            .build().url.toString() shouldContain "&categories[]=3"
    }

    "url for job category and city" {
        justJoinItRequestBuilder.apply { city = "Łomianki"; jobCategory = JobCategory.Php }
            .build().url.toString() shouldBe "https://api.justjoin.it/v2/user-panel/offers/count?withSalary=false&salaryCurrencies=PLN&categories[]=3"
    }
})