package org.dga.shalion.impl.rest.v1.responses

data class StudentResponse(
    val id: Long,
    val name: String,
    val schoolId: Long,
    val schoolName: String,
)
