package com.woowahan.ordering.domain.model

sealed class Result<T> {
    class Success<S>(val value: S): Result<S>()
    class Failure(val cause: Exception): Result<Nothing>()
    object Loading: Result<Nothing>()
}