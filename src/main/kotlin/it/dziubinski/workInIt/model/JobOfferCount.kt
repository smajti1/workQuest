package it.dziubinski.workInIt.model

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
}

enum class JobPortal {
    JUST_JOIN_IT,
    NO_FLUFF_JOBS_COM,
}

enum class JobCategory {
    Total,
    Kotlin,
    Php,
}