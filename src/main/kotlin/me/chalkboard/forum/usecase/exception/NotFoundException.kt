package me.chalkboard.forum.usecase.exception

import java.lang.RuntimeException

class NotFoundException(
    message: String?
) : RuntimeException(message)