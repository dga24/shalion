package org.dga.shalion.impl.rest.v1.requests

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class SchoolCreateRequest(
    @field:NotBlank()
    val name: String,
    @field:Min(value = 50)
    @field:Max(value = 2000)
    val capacity: Int,
)
