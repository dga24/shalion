package org.dga.shalion.impl.rest.v1

import jakarta.validation.Valid
import org.dga.shalion.impl.rest.v1.requests.StudentCreateRequest
import org.dga.shalion.impl.rest.v1.requests.StudentUpdateRequest
import org.dga.shalion.impl.rest.v1.responses.PageResponse
import org.dga.shalion.impl.rest.v1.responses.StudentResponse
import org.dga.shalion.impl.rest.v1.utils.ControllerConstants.PAGE_SIZE
import org.dga.shalion.impl.service.StudentService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/students")
@Validated
class StudentController(
    private val studentService: StudentService,
) {
    @PostMapping
    fun create(
        @RequestBody @Valid request: StudentCreateRequest,
    ): ResponseEntity<StudentResponse> =
        studentService.create(request = request).run {
            ResponseEntity.status(HttpStatus.CREATED).body(this)
        }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
    ): ResponseEntity<StudentResponse> =
        studentService.findById(id = id).run {
            ResponseEntity.ok(this)
        }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: StudentUpdateRequest,
    ): ResponseEntity<Unit> {
        studentService.update(id = id, request = request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        studentService.delete(id = id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchByNameInSchool(
        @RequestParam schoolId: Long,
        @RequestParam name: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "$PAGE_SIZE") size: Int,
        @RequestParam(defaultValue = "name") sort: String,
    ): ResponseEntity<PageResponse<StudentResponse>> {
        val pageable = PageRequest.of(page, size, Sort.by(sort))
        return studentService.searchByNameInSchool(schoolId = schoolId, name = name, pageable = pageable).run {
            ResponseEntity.ok(
                PageResponse(
                    results = this.content,
                    totalElements = this.totalElements,
                    totalPages = this.totalPages,
                    page = this.number,
                    size = this.size,
                ),
            )
        }
    }
}
