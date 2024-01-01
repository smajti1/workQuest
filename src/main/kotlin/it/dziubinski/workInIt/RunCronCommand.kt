package it.dziubinski.workInIt

import it.dziubinski.workInIt.cron.BulldogJobCron
import it.dziubinski.workInIt.cron.JustJoinItCron
import it.dziubinski.workInIt.cron.NoFluffJobsComCron
import it.dziubinski.workInIt.cron.SolidJobsCron
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class RunCronCommand(
    val justJoinItCron: JustJoinItCron,
    val noFluffJobsComCron: NoFluffJobsComCron,
    val solidJobsCron: SolidJobsCron,
    val bulldogJobCron: BulldogJobCron,
) {

    @ShellMethod(key = ["runAllCron", "run-all-cron"])
    fun executeRunAllCron(disableSleepBetween: Boolean = false): String {
        var countCronFunctions = 0
        val arrayOfCronFunctions = justJoinItCron.getCronFunctionArray() +
                noFluffJobsComCron.getCronFunctionArray() +
                solidJobsCron.getCronFunctionArray() +
                bulldogJobCron.getCronFunctionArray()

        for (cronFunction in arrayOfCronFunctions) {
            println(cronFunction.toString())
            cronFunction.invoke()
            if (!disableSleepBetween) Thread.sleep(2_000)
            countCronFunctions++
        }
        return "Done $countCronFunctions cron functions :)"
    }

    @ShellMethod(key = ["runCron", "run-cron"])
    fun executeSingleCron(
        @ShellOption cronName: String,
        disableSleepBetween: Boolean = false,
    ): String {
        val arrayOfCronFunctions = when (cronName) {
            "justJoinItCron" -> justJoinItCron.getCronFunctionArray()
            "noFluffJobsComCron" -> noFluffJobsComCron.getCronFunctionArray()
            "solidJobsCron" -> solidJobsCron.getCronFunctionArray()
            "bulldogJobCron" -> bulldogJobCron.getCronFunctionArray()
            else -> null
        }
        if (arrayOfCronFunctions != null) {
            for (cronFunction in arrayOfCronFunctions) {
                println(cronFunction.toString())
                cronFunction.invoke()
                if (!disableSleepBetween) Thread.sleep(2_000)
            }
            return "Done cron function :)"
        }
        return "Done but no cron with '$cronName' found!:)"
    }
}