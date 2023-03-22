package br.heizer.shoppinglists.infrastructure.timezone

import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class TimeZoneConfiguration(
    @Value("\${server.timezone}")
    private val serverTimeZone: String
) {
    private val logger: Logger = LoggerFactory.getLogger(TimeZoneConfiguration::class.qualifiedName)

    @PostConstruct
    fun initialize() {
        logger.info("Server timezone is set to $serverTimeZone")
        TimeZone.setDefault(TimeZone.getTimeZone(serverTimeZone))
    }
}