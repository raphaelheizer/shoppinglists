package br.heizer.shoppinglists.infrastructure.security.token.jwt.filters

import br.heizer.shoppinglists.infrastructure.security.token.jwt.JwtProperties
import br.heizer.shoppinglists.infrastructure.security.token.jwt.JwtUtilities
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.IOException
import java.nio.charset.StandardCharsets.UTF_8
import javax.crypto.SecretKey
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtTokenValidatorFilter(
    @Value("\${application.name}")
    private val applicationName: String,
    private val jwtProperties: JwtProperties
) : OncePerRequestFilter() {
    private val bearerTokenPrefix = "Bearer "

    @Throws(ServletException::class, IOException::class)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val jwt = request.getHeader(jwtProperties.jwtHeader)
        if (null != jwt) {
            when {
                jwt.startsWith(bearerTokenPrefix) -> decodeAsBearer(jwt)
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun decodeAsBearer(token: String) {
        val jwt = token.removePrefix(bearerTokenPrefix)
        decodeJwt(jwt)
    }

    private fun decodeJwt(jwt: String) {
        try {
            val key: SecretKey = Keys.hmacShaKeyFor(
                jwtProperties.jwtSecret.toByteArray(UTF_8)
            )

            val claims = JwtUtilities
                .parser
                .requireIssuer(applicationName)
                .requireSubject("jwt-token")
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .body

            val username = claims["username"].toString()
            val authorities = claims["authorities"] as String?

            val auth: Authentication = UsernamePasswordAuthenticationToken(
                username,
                null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
            )

            SecurityContextHolder.getContext().authentication = auth
        } catch (e: Exception) {
            throw BadCredentialsException("Invalid credentials")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return jwtProperties.jwtIgnorePaths.contains(request.servletPath)
    }
}