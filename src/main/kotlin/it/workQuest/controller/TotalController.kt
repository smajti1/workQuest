package it.workQuest.controller

import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.repository.JobOfferCountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/total")
class TotalController(val jobOfferCountRepository: JobOfferCountRepository) {

    @GetMapping("/today")
    fun totalToday(
        @RequestParam jobCategory: JobCategory = JobCategory.Total,
    ): Iterable<JobOfferCount> {
        return jobOfferCountRepository.findByCategoryWhereCreatedIsToday(jobCategory)
    }
}