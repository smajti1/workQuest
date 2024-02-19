package it.workQuest.repository

import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.model.JobPortal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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

    @Query("select joc from JobOfferCount joc where date(joc.createdAt) = date(now()) and joc.city is null and joc.category = :jobCategory")
    fun findByCategoryWhereCreatedIsToday(@Param(value = "jobCategory") jobCategory: JobCategory): Iterable<JobOfferCount>
}