package it.dziubinski.workInIt

import it.dziubinski.workInIt.cron.bulldogJob.BulldogJobCron
import it.dziubinski.workInIt.cron.inHireIo.InHireIoCron
import it.dziubinski.workInIt.cron.indeedCom.IndeedComCron
import it.dziubinski.workInIt.cron.justJoinIt.JustJoinItCron
import it.dziubinski.workInIt.cron.noFluffJobsCom.NoFluffJobsComCron
import it.dziubinski.workInIt.cron.pracujPl.PracujPlCron
import it.dziubinski.workInIt.cron.solidJobs.SolidJobsCron
import it.dziubinski.workInIt.cron.theProtocol.TheProtocolCron
import it.dziubinski.workInIt.model.JobPortal
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@ShellComponent
class RunCronCommand(
    val justJoinItCron: JustJoinItCron,
    val noFluffJobsComCron: NoFluffJobsComCron,
    val solidJobsCron: SolidJobsCron,
    val bulldogJobCron: BulldogJobCron,
    val inHireIoCron: InHireIoCron,
    val pracujPlCron: PracujPlCron,
    val indeedComCron: IndeedComCron,
    val theProtocolCron: TheProtocolCron,
) {

    @ShellMethod(key = ["runCronAll", "run-cron-all"])
    fun executeRunAllCron(disableSleepBetween: Boolean = false): String {
        val startTime = System.currentTimeMillis()
        var countCronFunctions = 0
        val arrayOfCronFunctions = justJoinItCron.getCronFunctionArray() +
                noFluffJobsComCron.getCronFunctionArray() +
                solidJobsCron.getCronFunctionArray() +
                bulldogJobCron.getCronFunctionArray() +
                inHireIoCron.getCronFunctionArray() +
                pracujPlCron.getCronFunctionArray() +
                indeedComCron.getCronFunctionArray() +
                theProtocolCron.getCronFunctionArray()

        for (cronFunction in arrayOfCronFunctions) {
            println(cronFunction.toString())
            cronFunction.invoke()
            if (!disableSleepBetween) Thread.sleep(2_000)
            countCronFunctions++
        }
        val duration = (System.currentTimeMillis() - startTime).toDuration(DurationUnit.MILLISECONDS)
        return "Done $countCronFunctions cron functions :) in: [" + duration.toHumanMinutesAndSeconds() + "]"
    }

    @ShellMethod(key = ["runCron", "run-cron"])
    fun executeSingleCron(
        @ShellOption(defaultValue = "") cronName: String,
        disableSleepBetween: Boolean = false,
    ): String {
        val arrayOfCronFunctions = when (cronName) {
            JobPortal.JUST_JOIN_IT.toString() -> justJoinItCron.getCronFunctionArray()
            JobPortal.NO_FLUFF_JOBS_COM.toString() -> noFluffJobsComCron.getCronFunctionArray()
            JobPortal.SOLID_JOBS.toString() -> solidJobsCron.getCronFunctionArray()
            JobPortal.BULLDOG_JOB.toString() -> bulldogJobCron.getCronFunctionArray()
            JobPortal.IN_HIRE_IO.toString() -> inHireIoCron.getCronFunctionArray()
            JobPortal.PRACUJ_PL.toString() -> pracujPlCron.getCronFunctionArray()
            JobPortal.INDEED_COM.toString() -> indeedComCron.getCronFunctionArray()
            JobPortal.THE_PROTOCOL.toString() -> theProtocolCron.getCronFunctionArray()
            else -> null
        }
        if (arrayOfCronFunctions == null) {
            return "Done but no cron with '$cronName' found! Choose one of " + JobPortal.entries.map { it.toString() }
        }
        val startTime = System.currentTimeMillis()
        for (cronFunction in arrayOfCronFunctions) {
            println(cronFunction.toString())
            cronFunction.invoke()
            if (!disableSleepBetween) Thread.sleep(2_000)
        }
        val duration = (System.currentTimeMillis() - startTime).toDuration(DurationUnit.MILLISECONDS)
        return "Done cron function :) in: [" + duration.toHumanMinutesAndSeconds() + "]"
    }
}