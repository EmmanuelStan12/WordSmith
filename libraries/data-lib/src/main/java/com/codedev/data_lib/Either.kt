package com.codedev.data_lib

sealed interface Either<T, R> {
    data class Success<T, R>(val data: T): Either<T, R>

    data class Failure<T, R>(val reason: R? = null): Either<T, R>

}