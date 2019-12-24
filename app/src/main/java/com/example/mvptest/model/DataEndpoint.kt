package com.example.mvptest.model

import androidx.annotation.NonNull
import retrofit2.Call
import retrofit2.http.GET

interface DataEndpoint {
@NonNull
@GET("json/JSONSample.json")
fun search(): Call<DefaultResponse>
}