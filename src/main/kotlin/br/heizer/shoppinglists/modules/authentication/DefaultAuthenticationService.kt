package br.heizer.shoppinglists.modules.authentication

import br.heizer.shoppinglists.infrastructure.security.authentication.AuthenticationProvider
import br.heizer.shoppinglists.infrastructure.security.authentication.AuthenticationToken
import br.heizer.shoppinglists.infrastructure.security.authentication.Role
import br.heizer.shoppinglists.infrastructure.security.tokens.jwt.JwtProperties
import br.heizer.shoppinglists.infrastructure.security.tokens.jwt.JwtUtilities
import br.heizer.shoppinglists.infrastructure.security.user.UserDetailsService
import br.heizer.shoppinglists.infrastructure.security.user.exceptions.UserNotFoundException
import br.heizer.shoppinglists.modules.users.User
import br.heizer.shoppinglists.modules.users.UserRepository
import br.heizer.shoppinglists.modules.users.exceptions.UserAlreadyExistsException
import br.heizer.shoppinglists.modules.users.model.PasswordChangeForm
import br.heizer.shoppinglists.modules.users.model.SignUpForm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.Principal
import java.util.*
import javax.security.auth.login.CredentialException

@Service
class DefaultAuthenticationService(
    private val usersRepository: UserRepository,
    private val authManager: AuthenticationProvider,
    private val userDetailsService: UserDetailsService,
    private val encoder: PasswordEncoder,
    private val jwtProperties: JwtProperties
) : AuthenticationService {

    override fun signIn(email: String, credentials: String): String {
        val token = UsernamePasswordAuthenticationToken(email, credentials)

        return authManager.authenticate(token)
            .let { authentication ->
                val authToken =
                    AuthenticationToken(authentication.name, authentication.authorities.first().authority)

                JwtUtilities
                    .convert(authToken, jwtProperties.secret)
                    .build()
            }
    }

    override fun signUp(signUpForm: SignUpForm) {
        usersRepository.findByEmail(signUpForm.email)
            .also {
                if (it != null)
                    throw UserAlreadyExistsException()
            }

        val uuid = UUID.randomUUID()
        val password = encoder.encode(uuid.toString())
        val defaultRole = Role.DEFAULT

        val user = User(
            email = signUpForm.email,
            avatarUrl = "",
            password = password,
            roles = listOf(defaultRole)
        )

        usersRepository.save(user)
    }

    override fun changePassword(passwordChange: PasswordChangeForm, principal: Principal) {
        val user = userDetailsService.loadUserByUsername(principal.name)

        if (user.isAccountNonExpired
            && user.isAccountNonLocked
            && user.isEnabled
            && encoder.matches(user.password, passwordChange.currentPassword)
        ) {
            val encodedPassword = encoder.encode(passwordChange.newPassword)
            val loadedUser = usersRepository.findByEmail(principal.name) ?: throw UserNotFoundException()

            loadedUser.password = encodedPassword
            usersRepository.save(loadedUser)
        } else
            throw CredentialException("Invalid credentials")
    }

}