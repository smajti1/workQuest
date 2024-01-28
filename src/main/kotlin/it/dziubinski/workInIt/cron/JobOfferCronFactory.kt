package it.dziubinski.workInIt.cron

import it.dziubinski.workInIt.cron.bulldogJob.BulldogJobCron
import it.dziubinski.workInIt.cron.inHireIo.InHireIoCron
import it.dziubinski.workInIt.cron.indeedCom.IndeedComCron
import it.dziubinski.workInIt.cron.itLeaders.ItLeadersCron
import it.dziubinski.workInIt.cron.justJoinIt.JustJoinItCron
import it.dziubinski.workInIt.cron.noFluffJobsCom.NoFluffJobsComCron
import it.dziubinski.workInIt.cron.pracujPl.PracujPlCron
import it.dziubinski.workInIt.cron.solidJobs.SolidJobsCron
import it.dziubinski.workInIt.cron.startupJobs.StartupJobsCron
import it.dziubinski.workInIt.cron.theProtocol.TheProtocolCron
import it.dziubinski.workInIt.model.JobPortal
import org.springframework.stereotype.Component

@Component
class JobOfferCronFactory(
    private val bulldogJobCron: BulldogJobCron,
    private val indeedComCron: IndeedComCron,
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
            JobPortal.INDEED_COM -> this.indeedComCron
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