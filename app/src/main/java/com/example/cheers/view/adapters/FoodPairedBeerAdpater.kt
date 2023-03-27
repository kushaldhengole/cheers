package com.example.cheers.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cheers.databinding.BeerHorizontalItemBinding
import com.example.cheers.databinding.BeerItemBinding
import com.example.cheers.model.dataModel.BeerDataModel
import com.example.cheers.model.dataModel.FoodPairedBeerDataModel

class FoodPairedBeerAdpater (val onClickAction: (beer:Long)->Unit):
        ListAdapter<FoodPairedBeerDataModel, FoodPairedBeerAdpater.ViewHolder>(
        DiffCallback()) {


        lateinit var binding: BeerHorizontalItemBinding
        class ViewHolder(  val view: BeerHorizontalItemBinding): RecyclerView.ViewHolder(view.root){
            var pairedFood:StringBuilder = java.lang.StringBuilder()
            fun bind(beer: FoodPairedBeerDataModel){
                beer.food_pairing.forEach {
                    Log.e("paired","${it}")
                    pairedFood.append("$it,")
                }
                view.pairedFood=pairedFood.toString()
                view.beer = beer

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            binding = BeerHorizontalItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return  ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
            holder.view.materialCardView.setOnClickListener {
                onClickAction.invoke(getItem(position).id?:0L)
            }

        }


        class DiffCallback : DiffUtil.ItemCallback<FoodPairedBeerDataModel>() {
            override fun areItemsTheSame(oldItem: FoodPairedBeerDataModel, newItem: FoodPairedBeerDataModel): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: FoodPairedBeerDataModel, newItem: FoodPairedBeerDataModel): Boolean {
                return oldItem==newItem
            }

        }
    }
