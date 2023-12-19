package it.dziubinski.workInIt.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import java.util.*

@Entity
class JobOfferCount(
    val jobPortal: JobPortal,
    val count: Int,
    val category: String?,
    val city: String?,
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID = UUID.randomUUID(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
}

enum class JobPortal {
    JUST_JOIN_IT
}