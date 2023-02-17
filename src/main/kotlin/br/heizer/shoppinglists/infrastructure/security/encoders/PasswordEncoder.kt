package br.heizer.shoppinglists.infrastructure.security.encoders

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}