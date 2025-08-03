package org.dga.shalion.impl.rest.v1.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SchoolUpdateRequest(
    @field:NotBlank()
    val name: String,
    @Min(50)
    @Max(2000)
    val capacity: Int,
)
