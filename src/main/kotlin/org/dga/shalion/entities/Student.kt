package org.dga.shalion.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import org.dga.shalion.impl.rest.v1.responses.StudentResponse

@Entity
@Table(name = "student")
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    @NotBlank
    val name: String,
    @ManyToOne(optional = false)
    @JoinColumn(name = "school_id", nullable = false)
    val school: School,
)

fun Student.toResponse() =
    StudentResponse(
        id = this.id!!,
        name = this.name,
        schoolId = this.school.id!!,
        schoolName = this.school.name,
    )
