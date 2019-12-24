package com.example.mvptest.model

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
@SerializedName("data") var data:List<dataDto>?=null,
@SerializedName("view") var view:Array<String>?=null
)