package io.challenge.releasemanager.service

import io.challenge.releasemanager.controller.dto.DeployRequest
import io.challenge.releasemanager.model.ServiceHistory
import io.challenge.releasemanager.model.SystemHistory
import io.challenge.releasemanager.repository.ServiceHistoryRepository
import io.challenge.releasemanager.repository.SystemHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class VersioningService(
    private val systemRepository: SystemHistoryRepository,
    private val serviceRepository: ServiceHistoryRepository
) {

    @Transactional
    fun saveOrUpdateService(request: DeployRequest): SystemHistory {
        if (!didServiceVersionChange(request)) {
            return findCurrentSystem().orElseThrow()
        }

        val serviceHistory = ServiceHistory()
        serviceHistory.serviceName = request.serviceName
        serviceHistory.version = request.serviceVersionNumber

        val sysHistory = createSystemHistoryEntity()

        serviceRepository.save(serviceHistory)
        return systemRepository.save(sysHistory)
    }

    fun getServicesState(systemVersion: Int): List<ServiceHistory> {
        val systemHistory = systemRepository.findByVersion(systemVersion).orElseThrow()
        val updatedToVersion = systemHistory.updatedAt


        return serviceRepository.findBySystemVersion(updatedToVersion)

    }

    private fun createSystemHistoryEntity(): SystemHistory {
        val sysHistory = SystemHistory()
        sysHistory.systemName = "Some name" // could be that there are more than 1 system in future

        var systemVersion = 0
        val mayBeSysHistory = findCurrentSystem()

        if (mayBeSysHistory.isPresent) {
            systemVersion = mayBeSysHistory.get().version + 1
        }

        sysHistory.version = systemVersion

        return sysHistory
    }

    private fun findCurrentSystem(): Optional<SystemHistory> {
        return systemRepository.findFirstByOrderByUpdatedAtDesc()
    }

    private fun didServiceVersionChange(request: DeployRequest): Boolean {
        val maybeServiceHistory = serviceRepository.findFirstByServiceNameOrderByUpdatedAtDesc(request.serviceName)

        if (maybeServiceHistory.isPresent && maybeServiceHistory.get().version == request.serviceVersionNumber) {
            return false
        }

        return true
    }
}