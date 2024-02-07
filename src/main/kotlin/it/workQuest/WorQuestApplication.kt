package it.workQuest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["it.workQuest"])
class WorkQuestApplication

fun main(args: Array<String>) {
    runApplication<WorkQuestApplication>(*args)
}
