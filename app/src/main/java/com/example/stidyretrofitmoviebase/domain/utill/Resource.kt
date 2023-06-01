package com.example.stidyretrofitmoviebase.domain.utill

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    abstract fun handle(handleResult: HandleResult<T>)
    class Success<T>(data: T) : Resource<T>(data) {

        override fun handle(handleResult: HandleResult<T>) {
            handleResult.handleSuccess(data)
        }
    }
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message) {
        override fun handle(handleResult: HandleResult<T>) {
            handleResult.handleError(message, data)
        }
    }
}

interface HandleResult<T> {
    fun handleSuccess(data: T?)
    fun handleError(message: String?, data: T?)
}