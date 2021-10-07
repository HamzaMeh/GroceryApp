package com.archestro.grocery.domain.usecases.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.archestro.grocery.data.source.remote.ApiErrorHandle
import com.archestro.grocery.data.source.remote.BaseResponse
import com.archestro.grocery.data.source.remote.model.response.category.Category
import com.archestro.grocery.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseUseCase<Response>(private val apiErrorHandle: ApiErrorHandle? = null) where Response : Any {

    abstract suspend fun run(paramter: Any?): LiveData<List<Response>>

    open fun invoke(
        showLoader: Boolean = true,
        showApiError: Boolean = true,
        scope: CoroutineScope,
        paramter: Any?
    ): MutableLiveData<Outcome<List<Response>>> {
        val resultLiveData = MutableLiveData<Outcome<List<Response>>>()
        val backgroundJob = scope.async {
            run(paramter)
        }
        scope.launch {
            try {
                backgroundJob.await().let {
                    if (showApiError && it is BaseResponse<*>) {
                        if ((it as BaseResponse<*>).responseCode == Constants.SUCCESS_CODE) {
                            resultLiveData.value = Outcome.Success<List<Response>>(it)
                        } else {
                            resultLiveData.value =
                                Outcome.Error(
                                    showErrorDialog = showApiError,
                                    message = (it as BaseResponse<*>).responseMsg
                                )
                        }
                    } else {
                        resultLiveData.value = Outcome.Success(it)
                    }
                }
            } catch (ex: Exception) {
                if (ex is HttpException || ex is UnknownHostException || ex is SocketTimeoutException) {
                    resultLiveData.value = Outcome.NetworkError(ex)
                } else {
                    resultLiveData.value = Outcome.Error(e = ex, showErrorDialog = showApiError)
                }
            } finally {
                resultLiveData.value = Outcome.End()
            }
        }

        return resultLiveData
    }

    open fun invoke(
        scope: CoroutineScope,
        paramter: Any?,
        onResult: (UseCaseResponse<Response>)
    ) {
        val backgroundJob = scope.async {
            run(paramter)
        }
        scope.launch {
            try {
                backgroundJob.await().let {
                    onResult.onSuccess(it)
                }
            } catch (ex: Exception) {
                onResult.onError(apiErrorHandle?.traceErrorException(ex))
            }
        }
    }
}