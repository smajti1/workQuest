package it.workQuest.controller

import it.workQuest.model.JobCategory
import it.workQuest.repository.JobOfferCountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("api/v1/job-offer-total")
class JobOfferTotalController(val jobOfferCountRepository: JobOfferCountRepository) {

    @GetMapping
    fun listByCityWithTotalOfferNumber(): MutableMap<String, MutableMap<String, Int>> {
        val jobOfferCountTotalList = jobOfferCountRepository.findByCategoryOrderByCreatedAt(JobCategory.Total)
        val result = mutableMapOf<String, MutableMap<String, Int>>()
        for (jobOfferCount in jobOfferCountTotalList) {
            val cityOrTotal = jobOfferCount.city ?: "Total"
            val day = jobOfferCount.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            if (result[cityOrTotal] == null) {
                result[cityOrTotal] = mutableMapOf(day to jobOfferCount.count)
            } else {
                val countForDay = result[cityOrTotal]?.get(day) ?: 0
                result[cityOrTotal]?.set(day, countForDay + jobOfferCount.count)
            }
        }

        return result
    }
}