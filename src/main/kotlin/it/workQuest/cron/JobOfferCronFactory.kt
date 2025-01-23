package it.workQuest.cron

import it.workQuest.cron.bulldogJob.BulldogJobCron
import it.workQuest.cron.inHireIo.InHireIoCron
import it.workQuest.cron.itLeaders.ItLeadersCron
import it.workQuest.cron.justJoinIt.JustJoinItCron
import it.workQuest.cron.noFluffJobsCom.NoFluffJobsComCron
import it.workQuest.cron.pracujPl.PracujPlCron
import it.workQuest.cron.solidJobs.SolidJobsCron
import it.workQuest.cron.startupJobs.StartupJobsCron
import it.workQuest.cron.theProtocol.TheProtocolCron
import it.workQuest.model.JobPortal
import org.springframework.stereotype.Component

@Component
class JobOfferCronFactory(
    private val bulldogJobCron: BulldogJobCron,
    private val inHireIoCron: InHireIoCron,
    private val itLeadersCron: ItLeadersCron,
    private val justJoinItCron: JustJoinItCron,
    private val noFluffJobsComCron: NoFluffJobsComCron,
    private val pracujPlCron: PracujPlCron,
    private val solidJobsCron: SolidJobsCron,
    private val startupJobsCron: StartupJobsCron,
    private val theProtocolCron: TheProtocolCron,
) {

    fun get(jobPortal: JobPortal): JobOfferCronInterface {
        return when (jobPortal) {
            JobPortal.BULLDOG_JOB -> this.bulldogJobCron
            JobPortal.IN_HIRE_IO -> this.inHireIoCron
            JobPortal.IT_LEADERS -> this.itLeadersCron
            JobPortal.JUST_JOIN_IT -> this.justJoinItCron
            JobPortal.NO_FLUFF_JOBS_COM -> this.noFluffJobsComCron
            JobPortal.PRACUJ_PL -> this.pracujPlCron
            JobPortal.SOLID_JOBS -> this.solidJobsCron
            JobPortal.STARTUP_JOBS -> this.startupJobsCron
            JobPortal.THE_PROTOCOL -> this.theProtocolCron
        }
    }
}