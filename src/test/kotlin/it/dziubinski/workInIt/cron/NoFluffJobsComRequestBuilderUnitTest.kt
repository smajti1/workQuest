package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import it.dziubinski.workInIt.cron.noFluffJobsCom.NoFluffJobsComRequestBuilder
import it.dziubinski.workInIt.model.JobCategory

class NoFluffJobsComRequestBuilderUnitTest(
    private val noFluffJobsComRequestBuilder: NoFluffJobsComRequestBuilder = NoFluffJobsComRequestBuilder(),
) : StringSpec({

    "should return url for total" {
        noFluffJobsComRequestBuilder.build().url.toString() shouldBe "https://nofluffjobs.com/api/search/posting?pageSize=0&salaryCurrency=PLN&salaryPeriod=month&region=pl"
    }

    "builder should contain application/infiniteSearch+json header" {
        noFluffJobsComRequestBuilder.build().headers.toString() shouldBe "{Content-Type=[application/infiniteSearch+json]}"
    }

    "body should contain city name Warszawa" {
        String(noFluffJobsComRequestBuilder.apply { city = "Warsaw" }.build().body.toByteArray()) shouldBe "{\"rawSearch\":\" city=Warszawa\",\"pageSize\":0}"
    }

    "url fo city Warsaw and kotlin" {
        String(noFluffJobsComRequestBuilder.apply { city = "Warsaw"; jobCategory = JobCategory.Kotlin }
            .build().body.toByteArray()) shouldBe "{\"rawSearch\":\"requirement=Kotlin city=Warszawa\",\"pageSize\":0}"
    }

    "url fo unknown city PHP" {
        String(noFluffJobsComRequestBuilder.apply { city = "Łomianki"; jobCategory = JobCategory.Php }
            .build().body.toByteArray()) shouldBe "{\"rawSearch\":\"requirement=PHP\",\"pageSize\":0}"
    }
})