package io.challenge.releasemanager.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@ConfigurationProperties("spring.datasource.app")
@EnableJpaRepositories(basePackages = ["io.challenge.releasemanager"])
@EnableJpaAuditing
class AppDbConfig {
    @Bean("appHikaryConfig")
    @ConfigurationProperties("spring.datasource.app.hikari")
    fun hikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean("appDataSource")
    fun dataSource(@Qualifier("appHikaryConfig") hikariConfig: HikariConfig?): DataSource {
        return HikariDataSource(hikariConfig)
    }

    @Bean
    fun entityManagerFactoryBuilder(): EntityManagerFactoryBuilder? {
        return EntityManagerFactoryBuilder(HibernateJpaVendorAdapter(), HashMap<String, Any?>(), null)
    }

    @Bean
    fun entityManagerFactory(
        @Qualifier("appDataSource") dataSource: DataSource,
        builder: EntityManagerFactoryBuilder
    ): LocalContainerEntityManagerFactoryBean {
        return builder
            .dataSource(dataSource)
            .packages("io.challenge.releasemanager")
            .persistenceUnit("postgres")
            .build()
    }

    @Bean
    fun transactionManager(entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager? {
        return JpaTransactionManager(entityManagerFactory!!)
    }
}