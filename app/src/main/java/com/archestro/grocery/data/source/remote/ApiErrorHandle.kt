package com.archestro.grocery.data.source.remote

import com.archestro.grocery.data.source.remote.model.ErrorModel
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


/**
 * This class trace exceptions(api call or parse data or connection errors) &
 * depending on what exception returns a [ErrorModel]
 *
 * */
class ApiErrorHandle {

    fun traceErrorException(throwable: Throwable?):ErrorModel{

        val errorModel:ErrorModel?=when(throwable)
        {
            is HttpException ->{

                if(throwable.code()==401)
                {
                    ErrorModel(throwable.message(),
                        throwable.code(),
                        ErrorModel.ErrorStatus.UNAUTHORIZED)
                }
                else
                {
                    getHttpError(throwable.response()?.body() as ResponseBody?)
                }
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.TIMEOUT)
            }

            // handle connection error
            is IOException -> {
                ErrorModel(throwable.message, ErrorModel.ErrorStatus.NO_CONNECTION)
            }

            //handle null pointer exception
            is NullPointerException->
            {
                ErrorModel(throwable.message,ErrorModel.ErrorStatus.NULLPOINTER)
            }
            else -> null
        }
        return errorModel ?: ErrorModel("No Defined Error!", 0, ErrorModel.ErrorStatus.BAD_RESPONSE)
    }

    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            ErrorModel(body.toString(), 400, ErrorModel.ErrorStatus.BAD_RESPONSE)
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(message = e.message, errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED)
        }

    }
}