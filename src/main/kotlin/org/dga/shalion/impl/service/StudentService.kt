package org.dga.shalion.impl.service

import jakarta.transaction.Transactional
import org.dga.shalion.entities.Student
import org.dga.shalion.entities.toResponse
import org.dga.shalion.impl.rest.v1.requests.StudentCreateRequest
import org.dga.shalion.impl.rest.v1.requests.StudentUpdateRequest
import org.dga.shalion.impl.rest.v1.responses.StudentResponse
import org.dga.shalion.repositories.SchoolRepository
import org.dga.shalion.repositories.StudentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository,
    private val schoolRepository: SchoolRepository,
) {
    @Transactional
    fun create(request: StudentCreateRequest): StudentResponse {
        val school =
            schoolRepository.findById(request.schoolId)
                .orElseThrow { NoSuchElementException("School not found") }

        val enrolledCount = studentRepository.countBySchoolId(school.id!!)
        if (enrolledCount >= school.capacity) {
            throw IllegalStateException("School '${school.name}' is at maximum capacity.")
        }

        val student =
            Student(
                name = request.name,
                school = school,
            )

        return studentRepository.save(student).toResponse()
    }

    fun findById(id: Long): StudentResponse =
        studentRepository.findById(id)
            .orElseThrow { NoSuchElementException("Student not found") }
            .toResponse()

    @Transactional
    fun update(
        id: Long,
        request: StudentUpdateRequest,
    ) {
        val existingStudent =
            studentRepository.findById(id)
                .orElseThrow { NoSuchElementException("Student not found") }

        val newSchool =
            schoolRepository.findById(request.schoolId)
                .orElseThrow { NoSuchElementException("School not found") }

        if (existingStudent.school.id != newSchool.id) {
            val enrolledCount = studentRepository.countBySchoolId(newSchool.id!!)
            if (enrolledCount >= newSchool.capacity) {
                throw IllegalStateException("School '${newSchool.name}' is at maximum capacity.")
            }
        }
        studentRepository.save(Student(id = id, name = request.name, school = newSchool))
    }

    fun delete(id: Long) {
        studentRepository.deleteById(id)
    }

    fun searchByNameInSchool(
        schoolId: Long,
        name: String,
        pageable: Pageable,
    ): Page<StudentResponse> =
        studentRepository.findBySchoolIdAndNameContainingIgnoreCase(schoolId, name, pageable)
            .map { it.toResponse() }

    fun findAllBySchool(
        schoolId: Long,
        pageable: Pageable,
    ): Page<Student> = studentRepository.findBySchoolId(schoolId, pageable)
}
