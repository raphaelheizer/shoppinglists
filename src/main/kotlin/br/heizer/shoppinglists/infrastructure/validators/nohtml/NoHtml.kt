package br.heizer.shoppinglists.infrastructure.validators.nohtml

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.PROPERTY_GETTER
)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [NoHtmlValidator::class])
annotation class NoHtml(
    val message: String = "Html tags or scripts are not allowed",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)