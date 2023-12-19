package it.dziubinski.workInIt.repository

import it.dziubinski.workInIt.model.JobOfferCount
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JobOfferCountRepository : JpaRepository<JobOfferCount, UUID> {
}