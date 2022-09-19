package io.challenge.releasemanager.repository

import io.challenge.releasemanager.model.SystemHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SystemHistoryRepository : JpaRepository<SystemHistory, Long> {

    fun findFirstByOrderByUpdatedAtDesc(): Optional<SystemHistory>

    fun findByVersion(version: Int): Optional<SystemHistory>
}