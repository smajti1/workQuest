package it.dziubinski.workInIt.controller

import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import it.dziubinski.workInIt.repository.JobOfferCountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/job-offer")
class JobOfferController(val jobOfferCountRepository: JobOfferCountRepository) {

    @GetMapping
    fun list(): Map<JobPortal, List<JobOfferCount>> {
        return jobOfferCountRepository.findAll().groupBy { it.jobPortal }
    }
}