package br.heizer.shoppinglists.infrastructure.timezone

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.logging.Logger

@Configuration
class TimeZoneConfiguration {

    @Value("\${server.timezone}")
    private lateinit var serverTimeZone: String

    @PostConstruct
    fun initialize() {
        Logger.getGlobal().info("Server timezone is set to $serverTimeZone")
        TimeZone.setDefault(TimeZone.getTimeZone(serverTimeZone))
    }
}