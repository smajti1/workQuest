package it.dziubinski.workInIt

import it.dziubinski.workInIt.cron.JustJoinItCron
import it.dziubinski.workInIt.cron.NoFluffJobsComCron
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
open class RunAllCronCommand(
    val test1Cron: JustJoinItCron,
    val test2Cron: NoFluffJobsComCron,
) {

    @ShellMethod(key = ["runAllCron", "run-all-cron"])
    fun executeCommand(): String {
        var countCronFunctions = 0
        val arrayOfCronFunctions = test1Cron.getCronFunctionArray() + test2Cron.getCronFunctionArray()
        for (cronFunction in arrayOfCronFunctions) {
            println(cronFunction.toString())
            cronFunction.invoke()
            Thread.sleep(1_000)
            countCronFunctions++
        }
        return "Done $countCronFunctions cron functions :)"
    }
}