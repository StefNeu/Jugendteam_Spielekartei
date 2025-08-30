package com.jugendteam_whv.Spielekartei

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomGameAdapter(context: Context,data: ArrayList<Game?>?) : ArrayAdapter<Game?>(context, R.layout.game_list_item, data!!){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val listData = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.game_list_item, parent, false)
        }
        val listImage = view!!.findViewById<ImageView>(R.id.imageView)
        val gameName = view.findViewById<TextView>(R.id.GameName)
        val gameCategory = view.findViewById<TextView>(R.id.GameCategory)
        gameName.text = listData?.name
        gameCategory.text = listData?.category?.getDisplayName(context)
        listImage.setImageResource(R.drawable.jugendteam_logo)

        return view
    }

}