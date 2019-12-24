package com.example.mvptest.model

import com.google.gson.annotations.SerializedName

data class dataDto(
        @SerializedName("name") var name:String?=null,
        @SerializedName("data") var data:innerDataDto?=null
)
data class variantDto(
        @SerializedName("id") var id:Int?=null,
        @SerializedName("text") var text:String?=null
)
data class innerDataDto(
        @SerializedName("text") var text:String?=null,
        @SerializedName("url") var url:String?=null,
        @SerializedName("selectedId") var selectedId:Int?=null,
        @SerializedName("variants") var variants:List<variantDto>?=null
)
