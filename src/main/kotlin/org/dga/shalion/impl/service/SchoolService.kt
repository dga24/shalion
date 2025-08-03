package org.dga.shalion.impl.service

import jakarta.transaction.Transactional
import org.dga.shalion.entities.School
import org.dga.shalion.entities.Student
import org.dga.shalion.impl.rest.v1.requests.SchoolCreateRequest
import org.dga.shalion.impl.rest.v1.requests.SchoolUpdateRequest
import org.dga.shalion.repositories.SchoolRepository
import org.dga.shalion.repositories.StudentRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SchoolService(
    private val schoolRepository: SchoolRepository,
    private val studentRepository: StudentRepository,
) {
    fun create(request: SchoolCreateRequest) {
        try {
            schoolRepository.save(School(name = request.name, capacity = request.capacity))
        } catch (ex: DataIntegrityViolationException) {
            throw IllegalArgumentException("School with name '${request.name}' already exists.")
        }
    }

    @Transactional
    fun update(
        id: Long,
        updated: SchoolUpdateRequest,
    ) {
        val existingSchool =
            schoolRepository.findById(id)
                .orElseThrow { NoSuchElementException("School not found") }

        if (updated.capacity < existingSchool.capacity) {
            val enrolledCount = studentRepository.countBySchoolId(id)
            if (enrolledCount > updated.capacity) {
                throw IllegalStateException(
                    "Cannot reduce capacity to ${updated.capacity}. School currently has $enrolledCount enrolled students.",
                )
            }
        }

        try {
            schoolRepository.save(School(id = id, name = updated.name, capacity = updated.capacity))
        } catch (ex: DataIntegrityViolationException) {
            throw IllegalArgumentException("School with name '${updated.name}' already exists.")
        }
    }

    fun delete(id: Long) {
        if (!schoolRepository.existsById(id)) {
            throw NoSuchElementException("School not found")
        }

        val studentCount = studentRepository.countBySchoolId(id)
        if (studentCount > 0) {
            throw IllegalStateException("Cannot delete school with $studentCount enrolled students. Transfer or remove students first.")
        }

        schoolRepository.deleteById(id)
    }

    fun findAll(pageable: Pageable): Page<School> = schoolRepository.findAll(pageable)

    fun findById(id: Long): School = schoolRepository.findById(id).orElseThrow { NoSuchElementException("School not found") }

    fun searchByName(
        name: String,
        pageable: Pageable,
    ): Page<School> = schoolRepository.findByNameContainingIgnoreCase(name, pageable)

    fun findSchoolWithStudents(
        id: Long,
        pageable: Pageable,
    ): Pair<School, Page<Student>> {
        val school =
            schoolRepository.findById(id)
                .orElseThrow { NoSuchElementException("School not found") }

        val studentPage = studentRepository.findBySchoolId(id, pageable)

        return Pair(school, studentPage)
    }
}
