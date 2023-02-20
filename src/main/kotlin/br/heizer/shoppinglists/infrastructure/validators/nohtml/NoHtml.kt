package br.heizer.shoppinglists.infrastructure.validators.nohtml

import br.heizer.shoppinglists.infrastructure.validators.secureurl.SecureUrlValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SecureUrlValidator::class])
annotation class NoHtml(
    val message: String = "Html tags or scripts are not allowed",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)