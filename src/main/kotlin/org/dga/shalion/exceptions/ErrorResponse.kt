package org.dga.shalion.exceptions

data class ErrorResponse(
    val message: String,
)

data class ValidationErrorResponse(
    val message: String,
    val errors: List<String>? = null,
)
