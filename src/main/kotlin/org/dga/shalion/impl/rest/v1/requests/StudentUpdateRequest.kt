package org.dga.shalion.impl.rest.v1.requests

import jakarta.validation.constraints.NotBlank

data class StudentUpdateRequest(
    @field:NotBlank()
    val name: String,
    @field:NotBlank()
    val schoolId: Long,
)
