package com.example.todoapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.Instant

@ControllerAdvice
class CustomExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException) =
        CustomErrorResponse(
            timestamp = Instant.now(),
            message = "Validation error",
            status = HttpStatus.BAD_REQUEST.value(),
            fieldErrors = exception.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }

        )
}