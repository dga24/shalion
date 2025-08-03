package org.dga.shalion.impl.rest.v1.responses

class SchoolWithStudentsResponse(
    val id: Long,
    val name: String,
    val capacity: Int,
    val students: PageResponse<StudentResponse>,
)
