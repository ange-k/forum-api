package me.chalkboard.forum.presentation.controller

import me.chalkboard.forum.model.Error
import me.chalkboard.forum.usecase.exception.InternalException
import me.chalkboard.forum.usecase.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ControllerAdvice
class ExceptionController{
    companion object {
        private val log = LoggerFactory.getLogger(ExceptionController::class.java)
    }

    @ExceptionHandler(InternalException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    suspend fun handleInternalApplicationError(exception: InternalException): ResponseEntity<Error> {
        log.info("想定外アプリケーションエラー:" + exception.message)
        log.info(exception.stackTraceToString())
        return ResponseEntity.internalServerError().body(Error(Error.Code.INTERNAL, exception.message.orEmpty()))
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    suspend fun handleNotFoundError(exception: NotFoundException): ResponseEntity<Error> {
        log.info("想定エラー:" + exception.message)
        log.info(exception.stackTraceToString())
        return ResponseEntity.internalServerError().body(Error(Error.Code.INTERNAL, exception.message.orEmpty()))
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    suspend fun handleInternalServerError(exception: Exception): ResponseEntity<Error> {
        log.error("想定外エラー:" + exception.message)
        log.info(exception.stackTraceToString())
        return ResponseEntity.internalServerError().body(Error(Error.Code.INTERNAL, exception.message.orEmpty()))
    }
}