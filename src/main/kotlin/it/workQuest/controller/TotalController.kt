package it.workQuest.controller

import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.repository.JobOfferCountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/total")
class TotalController(val jobOfferCountRepository: JobOfferCountRepository) {

    @GetMapping("/today")
    fun totalToday(): Iterable<JobOfferCount> {
        return jobOfferCountRepository.findByCategoryWhereCreatedIsToday(JobCategory.Total)
    }
}