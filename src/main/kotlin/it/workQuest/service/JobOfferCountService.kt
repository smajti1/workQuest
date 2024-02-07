package it.workQuest.service

import it.workQuest.model.JobCategory
import it.workQuest.model.JobOfferCount
import it.workQuest.model.JobPortal
import it.workQuest.repository.JobOfferCountRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class JobOfferCountService(
    private val jobOfferCountRepository: JobOfferCountRepository,
) {

    fun getJobPortalAlreadyCreatedCitiesByCategoryMap(jobPortal: JobPortal): Map<JobCategory, List<String?>> {
        val now = LocalDate.now()
        val startOfDay = LocalDateTime.of(now, LocalTime.MIN)
        val endOfDay = LocalDateTime.of(now, LocalTime.MAX)
        val jobPortalCountList = jobOfferCountRepository.findByJobPortalAndCreatedAtBetween(jobPortal, startOfDay, endOfDay)

        return jobPortalCountList.groupBy({ it.category }, { it.city })
    }

    fun saveNewJobOfferCount(jobPortal: JobPortal, category: JobCategory, city: String?, offerCount: Int) {
        val jobOfferCount = JobOfferCount(jobPortal, offerCount, category, city)
        jobOfferCountRepository.save(jobOfferCount)
    }
}