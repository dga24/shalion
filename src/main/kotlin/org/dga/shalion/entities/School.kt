package org.dga.shalion.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.dga.shalion.impl.rest.v1.responses.SchoolResponse

@Entity
@Table(name = "school")
data class School(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true, nullable = false)
    val name: String,
    @Column(nullable = false)
    @Min(50)
    @Max(2000)
    val capacity: Int,
)

fun School.toResponse() =
    SchoolResponse(
        id = this.id!!,
        name = this.name,
        capacity = this.capacity,
    )
