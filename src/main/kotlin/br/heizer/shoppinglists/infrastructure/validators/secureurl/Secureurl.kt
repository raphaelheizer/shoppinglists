package br.heizer.shoppinglists.infrastructure.validators.secureurl

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SecureUrlImpl::class])
annotation class SecureUrl(
    val message: String = "The given url is not secure",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)