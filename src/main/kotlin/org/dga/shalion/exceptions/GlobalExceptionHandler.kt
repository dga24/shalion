package org.dga.shalion.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleConflict(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorResponse(message = ex.message ?: "Conflict"))
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(ex: NoSuchElementException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(message = ex.message ?: "Resource not found"))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val errors =
            ex.bindingResult.fieldErrors.map {
                "${it.field}: ${it.defaultMessage}"
            }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ValidationErrorResponse(
                    message = "Validation failed",
                    errors = errors,
                ),
            )
    }
}
