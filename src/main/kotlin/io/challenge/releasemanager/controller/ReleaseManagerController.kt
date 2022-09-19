package io.challenge.releasemanager.controller

import io.challenge.releasemanager.controller.dto.DeployRequest
import io.challenge.releasemanager.controller.dto.ServiceHistoryResponse
import io.challenge.releasemanager.controller.dto.SystemVersionResponse
import io.challenge.releasemanager.controller.mapper.ServiceHistoryMapper
import io.challenge.releasemanager.service.VersioningService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/manager/v1"], produces = [MediaType.APPLICATION_JSON_VALUE])
class ReleaseManagerController(
    private val versioningService: VersioningService,
    private val mapper: ServiceHistoryMapper
) {

    @PostMapping("/deploy")
    fun saveDeployment(@RequestBody body: DeployRequest): SystemVersionResponse {
        val sysHistory = versioningService.saveOrUpdateService(body)
        return SystemVersionResponse(sysHistory.version)
    }

    @GetMapping("/services")
    fun getServices(@RequestParam systemVersion: Int): ServiceHistoryResponse {
        return ServiceHistoryResponse(versioningService.getServicesState(systemVersion)
            .map { s -> mapper.convertToDto(s) })
    }
}