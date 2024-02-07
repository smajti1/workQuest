package it.workQuest.repository

import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.model.JobPortal
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface JobOfferCountRepository : JpaRepository<JobOfferCount, UUID> {
    fun findByJobPortalAndCategoryAndCityOrderByCreatedAt(
        jobPortal: JobPortal,
        category: JobCategory,
        city: String?,
    ): Iterable<JobOfferCount>

    fun findByJobPortalAndCreatedAtBetween(
        jobPortal: JobPortal,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
    ): Iterable<JobOfferCount>
}