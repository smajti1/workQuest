package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import it.dziubinski.workInIt.model.JobCategory

class JustJoinItUrlBuilderUnitTest(
    private val justJoinItUrlBuilder: JustJoinItUrlBuilder = JustJoinItUrlBuilder(),
) : StringSpec({

    "should return url for total" {
        justJoinItUrlBuilder.toString() shouldBe "https://api.justjoin.it/v2/user-panel/offers/count?withSalary=false&salaryCurrencies=PLN"
    }

    "url should contain city name Warszawa" {
        justJoinItUrlBuilder.apply { city = "Warszawa" }.toString() shouldContain "Warszawa"
    }

    "url fo kotlin" {
        justJoinItUrlBuilder.apply { jobCategory = JobCategory.Kotlin }.toString() shouldContain "&keywords[]=kotlin"
    }

    "url for PHP" {
        justJoinItUrlBuilder.apply { jobCategory = JobCategory.Php }.toString() shouldContain "&categories[]=3"
    }

    "url for job category and city" {
        justJoinItUrlBuilder.apply { city = "Łomianki"; jobCategory = JobCategory.Php }
            .toString() shouldBe "https://api.justjoin.it/v2/user-panel/offers/count?withSalary=false&salaryCurrencies=PLN&city=Łomianki&categories[]=3"
    }
})