package br.heizer.shoppinglists.infrastructure.validators.nohtml

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NoHtmlValidator : ConstraintValidator<NoHtml, String> {

    companion object {
        val invalidChars = charArrayOf('<','.','*','/','?','>')
    }
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean = !containsHtml(value)

    private fun containsHtml(value: String?): Boolean =
        ((value?.indexOfAny(invalidChars) ?: -1) > -1)

}