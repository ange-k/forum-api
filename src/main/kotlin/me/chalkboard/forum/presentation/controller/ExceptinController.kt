package me.chalkboard.forum.presentation.controller

import me.chalkboard.forum.model.Error
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ControllerAdvice
class ExceptinController{
    companion object {
        private val log = LoggerFactory.getLogger(ExceptinController::class.java)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    suspend fun handleInternalServerError(exception: Exception): ResponseEntity<Error> {
        log.error("想定外エラー:" + exception.message)
        log.info(exception.stackTraceToString())
        return ResponseEntity.internalServerError().body(Error(Error.Code.INTERNAL, exception.message.orEmpty()))
    }
}