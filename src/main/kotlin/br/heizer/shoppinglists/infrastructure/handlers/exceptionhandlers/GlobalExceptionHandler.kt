package br.heizer.shoppinglists.infrastructure.handlers.exceptionhandlers

import br.heizer.shoppinglists.infrastructure.security.user.exceptions.UserNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI
import java.time.Instant

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(ex: UserNotFoundException, request: HttpServletRequest): ErrorResponse =
        ErrorResponse
            .builder(ex, HttpStatus.NOT_FOUND, ex.message ?: "No description")
            .title("User not found")
            .type(URI.create(request.requestURI))
            .property("timestamp", Instant.now())
            .apply {
                val cause = ex.cause?.message

                if (cause != null)
                    detail(cause)
            }
            .build()

}
