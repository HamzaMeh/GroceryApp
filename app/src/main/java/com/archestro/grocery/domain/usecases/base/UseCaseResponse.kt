package com.archestro.grocery.domain.usecases.base

import androidx.lifecycle.LiveData
import com.archestro.grocery.data.source.remote.model.ErrorModel

interface UseCaseResponse<Type> {
    fun onSuccess(result: LiveData<List<Type>>)

    fun onError(errorModel: ErrorModel?)
}