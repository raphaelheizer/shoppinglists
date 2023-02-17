package br.heizer.shoppinglists.infrastructure.security.token.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParserBuilder
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey

internal class JwtUtilities {
    companion object {
        val builder: JwtBuilder = Jwts.builder()
        val parser: JwtParserBuilder = Jwts.parserBuilder()

        private var secret: String? = null
        private var payload: Any? = null

        @Suppress("UnusedReceiverParameter")
        fun JwtBuilder.and() = JwtUtilities

        init {
            setDefault()
        }

        fun <T> convert(payload: T, secret: String): Companion {
            extractClaims(payload)

            if (Companion.secret == null) {
                val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(Charsets.UTF_8))
                builder.signWith(key)
            }
            return this
        }

        private fun setDefault() {
            builder
                .setSubject("jwt-token")
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + 30000000))
        }

        fun build(): String {
            payload = null
            secret = null
            return builder.compact()
        }

        @Suppress("UNCHECKED_CAST")
        private fun <T> extractClaims(obj: T) {
            val mapper = ObjectMapper()
            val claims = mapper.convertValue(obj, Map::class.java) as Map<String, Any>
            builder.addClaims(claims)
        }
    }
}