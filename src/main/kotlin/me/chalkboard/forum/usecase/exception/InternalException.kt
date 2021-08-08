package me.chalkboard.forum.usecase.exception

import java.lang.RuntimeException

class InternalException(
    message: String?
) : RuntimeException(message)