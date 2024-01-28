package it.dziubinski.workInIt.cron

interface JobOfferCronInterface {
    fun getCronFunctionArray(): Array<(Long) -> Unit>
}