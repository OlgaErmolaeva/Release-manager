package io.challenge.releasemanager.repository

import io.challenge.releasemanager.model.ServiceHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface ServiceHistoryRepository : JpaRepository<ServiceHistory, Long> {

    fun findFirstByServiceNameOrderByUpdatedAtDesc(serviceName: String) : Optional<ServiceHistory>
    fun findBySystemVersion(systemVersion: LocalDateTime) : List<ServiceHistory>
}