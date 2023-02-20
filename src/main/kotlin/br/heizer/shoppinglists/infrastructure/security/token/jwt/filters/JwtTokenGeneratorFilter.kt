package br.heizer.shoppinglists.infrastructure.security.token.jwt.filters

import br.heizer.shoppinglists.infrastructure.security.token.jwt.JwtProperties
import br.heizer.shoppinglists.infrastructure.security.token.jwt.JwtUtilities
import br.heizer.shoppinglists.infrastructure.security.token.jwt.JwtUtilities.Companion.and
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


class JwtTokenGeneratorFilter(
    private val applicationName: String,
    private val jwtProperties: JwtProperties
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        if (null != authentication) {
            val jwt: String =
                    JwtUtilities
                        .builder
                        .setIssuer(applicationName)
                        .claim("username", authentication.name)
                        .claim("authorities", populateAuthorities(authentication.authorities))
                        .and()
                        .build()

            response.setHeader(jwtProperties.header, jwt)
        }
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !jwtProperties.ignorePaths.contains(request.servletPath)
    }

    private fun populateAuthorities(collection: Collection<GrantedAuthority>): String? {
        val authoritiesSet: MutableSet<String> = HashSet()
        for (authority in collection) {
            authoritiesSet.add(authority.authority)
        }
        return java.lang.String.join(",", authoritiesSet)
    }

}