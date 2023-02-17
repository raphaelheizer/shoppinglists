package br.heizer.shoppinglists.infrastructure.security.web

import br.heizer.shoppinglists.infrastructure.security.csrf.CsrfCookieFilter
import br.heizer.shoppinglists.infrastructure.security.token.jwt.JwtProperties
import br.heizer.shoppinglists.infrastructure.security.token.jwt.filters.JwtTokenGeneratorFilter
import br.heizer.shoppinglists.infrastructure.security.token.jwt.filters.JwtTokenValidatorFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    @Value("\${spring.application.name}")
    private val applicationName: String,
    private val jwtProperties: JwtProperties
) {
    @Bean
    fun securityWebFilterChain(http: HttpSecurity): DefaultSecurityFilterChain? =
        http
            .sessionManagement()
            .sessionCreationPolicy(STATELESS)
            .and()
            .cors()
            .configurationSource { CorsWebConfiguration().corsConfigurationSource() }
            .and()
            .csrf()
            .ignoringRequestMatchers(
                AntPathRequestMatcher("*/**")
            )
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
            .addFilterAfter(CsrfCookieFilter(), BasicAuthenticationFilter::class.java)
            .addFilterAfter(
                JwtTokenGeneratorFilter(
                    applicationName,
                    jwtProperties
                ),
                BasicAuthenticationFilter::class.java
            )
            .addFilterAfter(
                JwtTokenValidatorFilter(
                    applicationName,
                    jwtProperties
                ),
                BasicAuthenticationFilter::class.java
            )
            .authorizeHttpRequests()
            .requestMatchers(
                AntPathRequestMatcher("*/**")
            )
            .permitAll()
            .and()
            .build()

    @Bean(name = ["jwtIgnorePaths"])
    fun jwtIgnorePaths(): Set<String> = setOf("/login")
}
