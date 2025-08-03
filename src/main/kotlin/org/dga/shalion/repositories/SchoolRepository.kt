package org.dga.shalion.repositories

import org.dga.shalion.entities.School
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolRepository : JpaRepository<School, Long> {
    fun findByNameContainingIgnoreCase(
        name: String,
        pageable: Pageable,
    ): Page<School>

    fun existsByName(name: String): Boolean
}
