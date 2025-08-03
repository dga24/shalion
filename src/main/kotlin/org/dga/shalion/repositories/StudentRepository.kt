package org.dga.shalion.repositories

import org.dga.shalion.entities.Student
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : JpaRepository<Student, Long> {
    fun findBySchoolIdAndNameContainingIgnoreCase(
        schoolId: Long,
        name: String,
        pageable: Pageable,
    ): Page<Student>

    fun findBySchoolId(
        schoolId: Long,
        pageable: Pageable,
    ): Page<Student>

    fun countBySchoolId(schoolId: Long): Long
}
