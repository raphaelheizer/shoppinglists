package br.heizer.shoppinglists.infrastructure.security.user

import jakarta.validation.Valid
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UserCredentialsRepository<T : UserCredentials> {
    fun findByEmail(@Valid email: String): T?

}