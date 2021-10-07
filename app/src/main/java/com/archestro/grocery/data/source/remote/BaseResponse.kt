package com.archestro.grocery.data.source.remote

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

    @SerializedName("status")
    var responseCode:Int,
    @SerializedName("message")
    var responseMsg:String?,
    @SerializedName("data")
    var data:T?=null,
    @SerializedName("errors")
    var error:ArrayList<Error>?=ArrayList()
)