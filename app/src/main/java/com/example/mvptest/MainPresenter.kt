package com.example.mvptest

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.mvptest.model.DefaultResponse
import com.example.mvptest.model.RestApi
import com.example.mvptest.model.dataDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class MainPresenter: MvpPresenter<MainView>() {
    private var searchRequest: Call<DefaultResponse>?=null
    var adapter = DataAdapter(viewState)
    var view:Array<String>?=null
    var data:List<dataDto>?=null
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
    private fun loadData(adapter: DataAdapter){
        cancelCurrentRequestIfNeeded()
        searchRequest= RestApi.getInstance()?.data()?.search()
        searchRequest?.enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Log.e("Request","Request failed")
            }

            override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                checkResponse(response,adapter)
            }
        })
    }

    private fun checkResponse(response: Response<DefaultResponse>,adapter: DataAdapter) {
        if (!response.isSuccessful) {
            Log.d("Request", "Server error")
            return
        }
        val body: DefaultResponse?=response.body()
        if (body == null) {
            Log.d("Request","Body is null")
            return
        }
        view=body.view
        data=body.data
        if (data == null) {
            Log.d("Request","Data is null")
            return
        }
        else //Toast.makeText(this,data.get(0).data?.selectedId.toString()+"Что-то пришло!",Toast.LENGTH_SHORT).show()
            adapter.replaceItems(data!!,view)
    }
    fun setTheAdapter(recyclerView: RecyclerView){
        recyclerView.adapter = adapter
    }
    fun setTheData(){
        if(data==null || view==null)
            loadData(adapter)
        else
            adapter.replaceItems(data!!,view)
    }
    fun makeMessage(position: Int){
        Log.d("Click","Making Message")
        viewState.showMessage("I am - "+adapter.data[position].name)
    }
}