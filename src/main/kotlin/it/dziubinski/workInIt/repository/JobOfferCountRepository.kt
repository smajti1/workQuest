package it.dziubinski.workInIt.repository

import it.dziubinski.workInIt.model.JobOfferCount
import it.dziubinski.workInIt.model.JobPortal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JobOfferCountRepository : JpaRepository<JobOfferCount, UUID> {
    fun findByJobPortalOrderByCreatedAt(jobPortal: JobPortal): Iterable<JobOfferCount>
}