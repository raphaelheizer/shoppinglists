package br.heizer.shoppinglists.infrastructure.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication!!.name
        val password = authentication.credentials.toString()
        val user = userDetailsService.loadUserByUsername(username)

        return if (isAccountEnabled(user) && passwordMatches(password, user.password))
            authorize(user, username, password)
        else
            throw BadCredentialsException("Invalid credentials")
    }

    private fun isAccountEnabled(user: UserDetails): Boolean =
        user.isAccountNonLocked || user.isAccountNonExpired || user.isEnabled

    private fun passwordMatches(password: String, userPassword: String): Boolean =
        passwordEncoder.matches(password, userPassword)

    private fun authorize(user: UserDetails, userName: String, password: String): UsernamePasswordAuthenticationToken {
        val authorities: List<GrantedAuthority> = user.authorities.toList()
        return UsernamePasswordAuthenticationToken(userName, password, authorities)
    }

    override fun supports(authentication: Class<*>?): Boolean =
        UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication!!)
}