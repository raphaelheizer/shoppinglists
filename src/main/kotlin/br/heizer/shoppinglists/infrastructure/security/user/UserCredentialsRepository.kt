package br.heizer.shoppinglists.infrastructure.security.user

import jakarta.validation.constraints.Email
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface UserCredentialsRepository<T : UserCredentials> {
    fun findByEmail(@Email email: String): T?

}