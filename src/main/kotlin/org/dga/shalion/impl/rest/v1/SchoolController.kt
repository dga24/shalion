package org.dga.shalion.impl.rest.v1

import jakarta.validation.Valid
import org.dga.shalion.entities.toResponse
import org.dga.shalion.impl.rest.v1.requests.SchoolCreateRequest
import org.dga.shalion.impl.rest.v1.requests.SchoolUpdateRequest
import org.dga.shalion.impl.rest.v1.responses.PageResponse
import org.dga.shalion.impl.rest.v1.responses.SchoolResponse
import org.dga.shalion.impl.rest.v1.responses.SchoolWithStudentsResponse
import org.dga.shalion.impl.rest.v1.utils.ControllerConstants.PAGE_SIZE
import org.dga.shalion.impl.service.SchoolService
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
@Validated
@RequestMapping("/api/v1/school")
class SchoolController(
    private val schoolService: SchoolService,
) {
    @PostMapping
    fun create(
        @Valid @RequestBody request: SchoolCreateRequest,
    ): ResponseEntity<Unit> =
        schoolService.create(request = request).run {
            ResponseEntity.status(HttpStatus.CREATED).build()
        }

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long,
    ): ResponseEntity<SchoolResponse> =
        schoolService.findById(id = id).run {
            ResponseEntity.ok(this.toResponse())
        }

    @GetMapping("/{id}/details")
    fun getDetailsById(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<SchoolWithStudentsResponse> {
        val pageable = PageRequest.of(page, size)
        val (school, studentPage) = schoolService.findSchoolWithStudents(id, pageable)

        return ResponseEntity.ok(
            SchoolWithStudentsResponse(
                id = school.id!!,
                name = school.name,
                capacity = school.capacity,
                students =
                    PageResponse(
                        results = studentPage.content.map { it.toResponse() },
                        page = studentPage.number,
                        size = studentPage.size,
                        totalElements = studentPage.totalElements,
                        totalPages = studentPage.totalPages,
                    ),
            ),
        )
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: SchoolUpdateRequest,
    ): ResponseEntity<Unit> =
        schoolService.update(id, request).run {
            ResponseEntity.noContent().build()
        }

    @GetMapping
    fun search(
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "$PAGE_SIZE") size: Int,
        @RequestParam(defaultValue = "name") sort: String,
    ): ResponseEntity<PageResponse<SchoolResponse>> {
        val pageable = PageRequest.of(page, size, Sort.by(sort))
        return if (name.isNullOrBlank()) {
            schoolService.findAll(pageable)
        } else {
            schoolService.searchByName(name, pageable)
        }.run {
            ResponseEntity.ok(
                PageResponse(
                    results = this.content.map { it.toResponse() },
                    page = this.number,
                    size = this.size,
                    totalElements = this.totalElements,
                    totalPages = this.totalPages,
                ),
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long,
    ): ResponseEntity<Unit> =
        schoolService.delete(id = id).run {
            ResponseEntity.noContent().build()
        }
}
