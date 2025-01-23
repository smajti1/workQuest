package it.workQuest.cron

import it.workQuest.model.JobPortal
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

const val SLEEP_TIME: Long = 500L

/**
 * @see <a href="https://crontab.guru">crontab.guru</a>
 */
@Component
@EnableScheduling
class ScheduleJobOffer(private val jobOfferCronFactory: JobOfferCronFactory) {

    @Scheduled(cron = "0 0,30 1,2 * * ?", zone = "Europe/Warsaw") // run every day at 1:00:00, 1:30:00, 2:00:00, 2:30:00
    fun scheduleHarderWebScrapPages() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.THE_PROTOCOL).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 0 3,4 * * ?", zone = "Europe/Warsaw") // run every day at 3:00:00, 4:00:00
    fun scheduleEasyWebScrapPages() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.BULLDOG_JOB).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.IN_HIRE_IO).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.IT_LEADERS).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.JUST_JOIN_IT).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.NO_FLUFF_JOBS_COM).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.PRACUJ_PL).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.SOLID_JOBS).getCronFunctionArray() +
                jobOfferCronFactory.get(JobPortal.STARTUP_JOBS).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }
}