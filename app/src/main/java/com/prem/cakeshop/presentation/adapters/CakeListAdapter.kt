package com.prem.cakeshop.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prem.cakeshop.R
import com.prem.cakeshop.domain.model.CakeInfo

class CakeListAdapter constructor(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<CakeListAdapter.CakeListViewHolder>() {

    private var cakesList: List<CakeInfo> = emptyList()

    fun setData(cakeInfoList: List<CakeInfo>) {
        cakesList = cakeInfoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cakeinfo_card_view, parent, false)
        return CakeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CakeListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(cakesList[position].image)
            .placeholder(R.drawable.default_placeholder)
            .into(holder.cakeImageview)
        holder.titleTextView.text = cakesList[position].title
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.fadein_animation)
        holder.itemView.setOnClickListener { listener.onItemClick(cakesList[position]) }
    }

    override fun getItemCount(): Int {
        return cakesList.size
    }

    class CakeListViewHolder(cakeInfoView: View) : RecyclerView.ViewHolder(cakeInfoView) {
        val titleTextView: TextView = cakeInfoView.findViewById(R.id.titleTextView)
        val cakeImageview: ImageView = cakeInfoView.findViewById(R.id.cakeImageView)
    }

    interface OnItemClickListener {
        fun onItemClick(item: CakeInfo)
    }
}