package com.example.mvptest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvptest.model.dataDto

class DataAdapter(listener:ViewHolder.clickListener):RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View, listener: clickListener): RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var text: TextView = itemView.findViewById(R.id.text)
        var pic: ImageView = itemView.findViewById(R.id.pic)
        var rGroup:RadioGroup = itemView.findViewById(R.id.radioGroup)
        var name=""
        var itemListener = listener
        init {
            itemView.setOnClickListener(this)
        }
        interface clickListener{
            fun onItemClick(position: Int)
        }

        override fun onClick(v: View?) {
            itemListener.onItemClick(adapterPosition)
        }
    }
    var data = mutableListOf<dataDto>()
    var itemListener=listener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.data_item,parent,false)
        return ViewHolder(view,itemListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.name = item.name!!
        if (item.data?.text != null) holder.text.text = item.data!!.text
        else holder.text.visibility = View.GONE
        if (item.data?.url != null) Glide.with(holder.pic).load(item.data!!.url).into(holder.pic)
        else holder.pic.visibility = View.GONE
        if (item.data?.variants != null) {
            holder.rGroup.orientation = RadioGroup.VERTICAL
            for (i in 0..item.data!!.variants!!.size - 1) {
                var rb = RadioButton(holder.rGroup.context)
                rb.text = item.data!!.variants?.get(i)!!.text
                rb.id = item.data!!.variants?.get(i)!!.id!!
//                rb.setOnClickListener {
//
//                }
                holder.rGroup.addView(rb)
            }
        } else holder.rGroup.visibility = View.GONE
    }

    fun replaceItems(list: List<dataDto>, view: Array<String>?) {
        for (i in 0..view!!.size-1) {
            for (j in 0..list.size - 1) {
                if (list[j].name == view[i])
                    data.add(list[j])
            }
        }
        notifyDataSetChanged()
    }
}

