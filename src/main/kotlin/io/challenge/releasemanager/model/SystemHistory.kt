package io.challenge.releasemanager.model

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "system_history")
@Entity
class SystemHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "system_name")
    var systemName: String = "",

    @CreatedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "version")
    var version: Int = 0
)
