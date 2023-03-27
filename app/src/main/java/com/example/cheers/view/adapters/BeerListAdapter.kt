package com.example.cheers.view.adapters

import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cheers.databinding.BeerItemBinding
import com.example.cheers.model.dataModel.BeerDataModel
import java.util.zip.Inflater


class BeerListAdapter(val onClickAction: (beer:Long)->Unit):ListAdapter<BeerDataModel,BeerListAdapter.ViewHolder>(
DiffCallback()) {

    lateinit var binding: BeerItemBinding
    class ViewHolder(  val view:BeerItemBinding):RecyclerView.ViewHolder(view.root){
        fun bind(beer: BeerDataModel){
           view.beer = beer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = BeerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.view.mcvBeer.setOnClickListener {
            onClickAction.invoke(getItem(position).id?:0L)
        }

    }


    class DiffCallback : DiffUtil.ItemCallback<BeerDataModel>() {
        override fun areItemsTheSame(oldItem: BeerDataModel, newItem: BeerDataModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: BeerDataModel, newItem: BeerDataModel): Boolean {
            return oldItem==newItem
        }

    }
}