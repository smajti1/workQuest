package it.dziubinski.workInIt.repository

import it.dziubinski.workInIt.model.JobCategory
import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JobOfferCountRepository : JpaRepository<JobOfferCount, UUID> {
    fun findByJobPortalAndCategoryAndCityOrderByCreatedAt(
        jobPortal: JobPortal,
        category: JobCategory,
        city: String?
    ): Iterable<JobOfferCount>
}