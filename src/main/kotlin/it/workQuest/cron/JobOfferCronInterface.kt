package it.workQuest.cron

interface JobOfferCronInterface {
    fun getCronFunctionArray(): Array<(Long) -> Unit>
}