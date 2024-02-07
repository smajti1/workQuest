package it.workQuest.cron

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import it.workQuest.getReflections
import org.springframework.scheduling.annotation.EnableScheduling

class ScheduleJobOfferUnitTest : StringSpec({
    "only one class should have @EnableScheduling annotation" {
        val annotationClass = getReflections().getTypesAnnotatedWith(EnableScheduling::class.java)
        annotationClass.count().shouldBeEqual(1)
        annotationClass.count { it.kotlin == ScheduleJobOffer::class }.shouldBeEqual(1)
    }
})