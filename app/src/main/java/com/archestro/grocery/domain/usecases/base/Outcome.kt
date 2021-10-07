package com.archestro.grocery.domain.usecases.base

import com.archestro.grocery.data.source.remote.model.ErrorModel


sealed class Outcome<T> {
    class Start<T> : Outcome<T>()
    class End<T> : Outcome<T>()
    class Success<T>(data: Any) : Outcome<T>()
    class HIDE<T>(var data: T) : Outcome<T>()
    class Failure<T>(val e: ErrorModel?) : Outcome<T>()
    class NetworkError<T>(val e: Throwable?) : Outcome<T>()
    class Errortemp<T>(message: String,data: T?=null): Outcome<T>()
    class Error<T>(
        val e: Exception? = null,
        var showErrorDialog: Boolean,
        var message: String? = null
    ) :
        Outcome<T>()
}