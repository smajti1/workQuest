package it.dziubinski.workInIt.cron

import it.dziubinski.workInIt.model.JobPortal
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

const val SLEEP_TIME: Long = 500L

@Component
@EnableScheduling
class ScheduleJobOffer(private val jobOfferCronFactory: JobOfferCronFactory) {

    @Scheduled(cron = "0 0 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:00:00
    fun scheduleBulldogJob() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.BULLDOG_JOB).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 5 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:05:00
    fun scheduleIndeedCom() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.INDEED_COM).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 10 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:10:00
    fun scheduleInHireIo() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.IN_HIRE_IO).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 15 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:15:00
    fun scheduleItLeaders() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.IT_LEADERS).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 20 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:20:00
    fun scheduleJustJoinIt() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.JUST_JOIN_IT).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 25 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:25:00
    fun scheduleNoFluffJobsCom() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.NO_FLUFF_JOBS_COM).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 30 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:30:00
    fun schedulePracujPl() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.PRACUJ_PL).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 35 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:35:00
    fun scheduleSolidJobs() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.SOLID_JOBS).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 40 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:40:00
    fun scheduleStartupJobs() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.STARTUP_JOBS).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }

    @Scheduled(cron = "0 45 4 * * ?", zone = "Europe/Warsaw") // run every day at 4:45:00
    fun scheduleTheProtocol() {
        val arrayOfCronFunctions = jobOfferCronFactory.get(JobPortal.THE_PROTOCOL).getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            cronFunction.invoke(SLEEP_TIME)
        }
    }
}