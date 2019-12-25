package com.example.mvptest

import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpView

interface MainView:MvpView {
    fun askForData()
    //fun askForAdapter(recyclerView: RecyclerView)
    fun showMessage(text:String)
}