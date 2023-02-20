package br.heizer.shoppinglists.infrastructure.validators.password

import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrlValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SecureUrlValidator::class])
annotation class Password(
    val message: String = "Invalid password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)