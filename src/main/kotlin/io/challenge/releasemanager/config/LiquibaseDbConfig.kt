package io.challenge.releasemanager.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@ConfigurationProperties("spring.datasource.liquibase")
class LiquibaseDbConfig {
    @Bean("liquibaseHikaryConfig")
    @ConfigurationProperties("spring.datasource.liquibase.hikari")
    fun hikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @LiquibaseDataSource
    @Bean
    fun dataSource(@Qualifier("liquibaseHikaryConfig") hikariConfig: HikariConfig?): DataSource {
        return HikariDataSource(hikariConfig)
    }
}