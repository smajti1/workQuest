package it.dziubinski.workInIt

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import it.dziubinski.workInIt.cron.JobOfferRequestCronAbstract
import it.dziubinski.workInIt.cron.JobOfferScrapWebPageCronAbstract
import org.springframework.scheduling.annotation.EnableScheduling
import kotlin.reflect.full.findAnnotation

class JobOfferCronUnitTest() : StringSpec({
    "all class that implements JobOfferRequestCronAbstract should have @EnableScheduling annotation" {
        val implementations = findImplementations(JobOfferRequestCronAbstract::class)

        implementations.filter { it.findAnnotation<EnableScheduling>() == null }.map { it.simpleName }.shouldBeEmpty()
    }

    "all class that implements JobOfferScrapWebPageCronAbstract should have @EnableScheduling annotation" {
        val implementations = findImplementations(JobOfferScrapWebPageCronAbstract::class)

        implementations.filter { it.findAnnotation<EnableScheduling>() == null }.map { it.simpleName }.shouldBeEmpty()
    }
})