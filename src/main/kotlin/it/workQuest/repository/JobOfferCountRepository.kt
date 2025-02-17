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
    fun findByCategoryOrderByCreatedAt(
        category: JobCategory,
    ): Iterable<JobOfferCount>

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

    @Query("select joc from JobOfferCount joc where date(joc.createdAt) = date(now()) and joc.city is null and joc.category = :jobCategory order by joc.jobPortal")
    fun findByCategoryWhereCreatedIsToday(@Param(value = "jobCategory") jobCategory: JobCategory): Iterable<JobOfferCount>
}