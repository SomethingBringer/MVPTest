package com.example.mvptest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter


class MainActivity : MvpAppCompatActivity(),MainView{
    @InjectPresenter
    lateinit var mainPresenter: MainPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recycler:RecyclerView = findViewById(R.id.dataList)
        askForAdapter(recycler)
        if (recycler.adapter?.itemCount==0)
            askForData()
    }
    override fun askForData() {
        mainPresenter.setTheData()
    }

    override fun askForAdapter(recyclerView: RecyclerView) {
        mainPresenter.setTheAdapter(recyclerView)
    }

    override fun onItemClick(position: Int) {
        Log.d("Click","Click is happening")
        mainPresenter.makeMessage(position)
    }

    override fun showMessage(text: String) {
        Log.d("Click","Showing")
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}
