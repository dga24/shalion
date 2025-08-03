package org.dga.shalion.impl.rest.v1.responses

data class PageResponse<T>(
    val results: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
)
