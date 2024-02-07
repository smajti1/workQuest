package it.workQuest.controller

import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.model.JobPortal
import it.workQuest.repository.JobOfferCountRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/job-offer")
class JobOfferController(val jobOfferCountRepository: JobOfferCountRepository) {

    @GetMapping
    fun list(): Map<JobPortal, List<JobOfferCount>> {
        return jobOfferCountRepository.findAll().groupBy { it.jobPortal }
    }

    @GetMapping("/{jobPortal}")
    fun list(
        @PathVariable("jobPortal") jobPortal: JobPortal,
        @RequestParam jobCategory: JobCategory = JobCategory.Total,
        @RequestParam city: String?
    ): Map<String, Int> {
        return jobOfferCountRepository.findByJobPortalAndCategoryAndCityOrderByCreatedAt(jobPortal, jobCategory, city)
            .associate { it.createdAt.toLocalDate().toString() to it.count }
    }
}