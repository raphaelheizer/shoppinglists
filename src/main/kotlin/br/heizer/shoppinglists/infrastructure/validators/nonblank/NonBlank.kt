package br.heizer.shoppinglists.infrastructure.validators.nonblank

import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrlValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SecureUrlValidator::class])
annotation class NonBlank(
    val message: String = "Field must not be blank",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)