package it.workQuest.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
class JobOfferCount(
    @Enumerated(EnumType.STRING) val jobPortal: JobPortal,
    val count: Int,
    @Enumerated(EnumType.STRING) val category: JobCategory,
    val city: String?,
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID = UUID.randomUUID(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        val cities = listOf(null, "Warsaw")
    }

    init {
        if (city != null && !cities.contains(city)) {
            throw JobOfferCountInvalidCityException(city)
        }
    }
}

enum class JobPortal {
    BULLDOG_JOB,
    IN_HIRE_IO,
    IT_LEADERS,
    JUST_JOIN_IT,
    NO_FLUFF_JOBS_COM,
    PRACUJ_PL,
    SOLID_JOBS,
    STARTUP_JOBS,
    THE_PROTOCOL,
}

enum class JobCategory {
    Total,
    Kotlin,
    Php,
}

class JobOfferCountInvalidCityException(invalidCity: String) : Exception("Incorrect city '${invalidCity} given!")