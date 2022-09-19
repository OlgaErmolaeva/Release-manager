package io.challenge.releasemanager.model

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name="service_history")
@Entity
@NamedNativeQuery(name = "ServiceHistory.findBySystemVersion", query = "SELECT " +
        "part.id, part.service_name, part.version, part.updated_at" +
        " FROM (" +
        " SELECT" +
        " ROW_NUMBER() OVER (PARTITION BY sh.service_name ORDER BY sh.updated_at DESC) AS row_number," +
        " sh.*" +
        " FROM" +
        " service_history sh" +
        " WHERE updated_at < ?) part" +
        " WHERE" +
        " part.row_number <= 1", resultClass = ServiceHistory::class)
class ServiceHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "service_name")
    var serviceName: String = "",

    @CreatedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "version")
    var version: Int = 0
)
