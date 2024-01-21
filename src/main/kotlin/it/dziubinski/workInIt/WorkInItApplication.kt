package it.dziubinski.workInIt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["it.dziubinski.workInIt"])
class WorkInItApplication

fun main(args: Array<String>) {
    runApplication<WorkInItApplication>(*args)
}
