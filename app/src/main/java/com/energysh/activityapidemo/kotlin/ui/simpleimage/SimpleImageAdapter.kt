package com.energysh.activityapidemo.kotlin.ui.simpleimage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.energysh.activityapidemo.R

class SimpleImageAdapter(val list : List<Int>) : RecyclerView.Adapter<SimpleImageAdapter.SimpleImageViewHolder>() {

    var onClickItemListener : ((simpleImageResId : Int)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleImageViewHolder {
        return SimpleImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item_simple_image, parent, false))
    }

    override fun onBindViewHolder(holder: SimpleImageViewHolder, position: Int) {
        holder.imageView.setImageResource(list[position])
        holder.imageView.setOnClickListener {
            onClickItemListener?.invoke(list[position])
        }
    }

    override fun getItemCount(): Int = list.size


    inner class SimpleImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView : AppCompatImageView = itemView.findViewById(R.id.iv_image)
    }



}