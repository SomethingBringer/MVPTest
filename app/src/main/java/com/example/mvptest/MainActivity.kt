package com.example.mvptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
//import com.arellomobile.mvp.MvpAppCompatActivity
//import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.mvptest.model.DefaultResponse
import com.example.mvptest.model.RestApi
import com.example.mvptest.model.dataDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){
//    @InjectPresenter
//    lateinit var mainPresenter: MainPresenter
    val adapter = DataAdapter()
    private var searchRequest: Call<DefaultResponse>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycler = findViewById<RecyclerView>(R.id.dataList)
        recycler.adapter=adapter
        load()
    }
    private fun cancelCurrentRequestIfNeeded() {
        if (searchRequest == null) {
            return
        }
        if (searchRequest!!.isCanceled) {
            searchRequest = null
            return
        }
        if (searchRequest!!.isExecuted) {
            searchRequest!!.cancel()
            searchRequest = null
        }
    }
    private fun load(){
        cancelCurrentRequestIfNeeded()
        searchRequest=RestApi.getInstance()?.data()?.search()
        searchRequest?.enqueue(object : Callback<DefaultResponse>{
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("Request","Request failed")
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                checkResponse(response)
            }
        })
    }

    private fun checkResponse(response: Response<DefaultResponse>) {
        if (!response.isSuccessful) {
            Log.d("Request", "Server error")
            return
        }
        val body:DefaultResponse?=response.body()
        if (body == null) {
            Log.d("Request","Body is null")
            return
        }
        val view:Array<String>?=body.view
        val data:List<dataDto>?=body.data
        if (data == null) {
            Log.d("Request","Data is null")
            return
        }
        else //Toast.makeText(this,data.get(0).data?.selectedId.toString()+"Что-то пришло!",Toast.LENGTH_SHORT).show()
            adapter.replaceItems(data,view)
    }
}
