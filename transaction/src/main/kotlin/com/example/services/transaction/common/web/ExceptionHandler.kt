package com.example.services.transaction.common.web

import com.example.services.transaction.domain.exception.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException.NotFound

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BusinessException::class)
    fun handlerBusinessException(exception: BusinessException): ResponseEntity<ApiError> =
        ResponseEntity(ApiError.of(exception), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(Exception::class)
    fun handlerGenericException(exception: Exception): ResponseEntity<ApiError> =
        ResponseEntity(ApiError.of(exception), HttpStatus.INTERNAL_SERVER_ERROR)
}