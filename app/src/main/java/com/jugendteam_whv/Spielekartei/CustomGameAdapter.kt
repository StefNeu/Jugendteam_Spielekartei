package com.jugendteam_whv.Spielekartei

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class CustomGameAdapter(private val data: List<Game>) : BaseAdapter(){
    override fun getCount(): Int = data.size
    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

}