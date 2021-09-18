package com.vspk.bookrest.exception.handler

import com.vspk.bookrest.exception.UserAlreadyVerifiedException
import com.vspk.bookrest.exception.UserVerificationException
import com.vspk.bookrest.exception.auth.JwtAuthenticationException
import com.vspk.bookrest.exception.auth.UserAlreadyExistsException
import com.vspk.bookrest.payload.AuthApiError
import java.util.function.Consumer
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [UserAlreadyExistsException::class])
    protected fun userAlreadyExists(ex: UserAlreadyExistsException): ResponseEntity<Any> {
        val authApiError = AuthApiError(ex.message)
        return buildResponseEntity(authApiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [JwtAuthenticationException::class])
    protected fun loginFailed(ex: JwtAuthenticationException): ResponseEntity<Any> {
        val authApiError = AuthApiError(ex.message)
        return buildResponseEntity(authApiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(value = [UserVerificationException::class])
    protected fun verificationException(ex: UserVerificationException): ResponseEntity<Any> {
        val authApiError = AuthApiError(ex.message)
        return buildResponseEntity(authApiError, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [UserAlreadyVerifiedException::class])
    protected fun userAlreadyExists(ex: UserAlreadyVerifiedException): ResponseEntity<Any> {
        val authApiError = AuthApiError(ex.message)
        return buildResponseEntity(authApiError, HttpStatus.BAD_REQUEST)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: MutableMap<String, String> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return buildResponseEntity(AuthApiError(errorMessage = "validation failed", validationErrors = errors), HttpStatus.BAD_REQUEST)
    }

    private fun buildResponseEntity(apiError: AuthApiError, status: HttpStatus): ResponseEntity<Any> {
        return ResponseEntity(apiError, status)
    }
}