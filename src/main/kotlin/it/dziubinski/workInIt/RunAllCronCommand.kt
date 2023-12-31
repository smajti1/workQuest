package it.dziubinski.workInIt

import it.dziubinski.workInIt.cron.JustJoinItCron
import it.dziubinski.workInIt.cron.NoFluffJobsComCron
import it.dziubinski.workInIt.cron.SolidJobsCron
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class RunAllCronCommand(
    val justJoinItCron: JustJoinItCron,
    val noFluffJobsComCron: NoFluffJobsComCron,
    val solidJobsCron: SolidJobsCron,
) {

    @ShellMethod(key = ["runAllCron", "run-all-cron"])
    fun executeCommand(): String {
        var countCronFunctions = 0
        val arrayOfCronFunctions = justJoinItCron.getCronFunctionArray() + noFluffJobsComCron.getCronFunctionArray() + solidJobsCron.getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            println(cronFunction.toString())
            cronFunction.invoke()
            Thread.sleep(1_000)
            countCronFunctions++
        }
        return "Done $countCronFunctions cron functions :)"
    }
}