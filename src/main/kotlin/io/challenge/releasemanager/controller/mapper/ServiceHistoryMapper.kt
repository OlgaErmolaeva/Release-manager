package io.challenge.releasemanager.controller.mapper

import io.challenge.releasemanager.controller.dto.ServiceHistoryDto
import io.challenge.releasemanager.model.ServiceHistory
import org.springframework.stereotype.Service

@Service
class ServiceHistoryMapper {

    fun convertToDto (entity: ServiceHistory) : ServiceHistoryDto {
        return ServiceHistoryDto(entity.serviceName, entity.version)
    }
}