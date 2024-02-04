package com.example.homework_21_reworked.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_21_reworked.databinding.ItemShopLayoutBinding
import com.example.homework_21_reworked.presentation.model.ShopItem

class ShopItemRecyclerAdapter : ListAdapter<ShopItem, ShopItemRecyclerAdapter.ShopViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemShopLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShopViewHolder(private val binding: ItemShopLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShopItem) {
            with(binding) {
                tvTitle.text = item.title
                tvPrice.text = item.price
                Glide.with(icImage.context)
                    .load(item.image)
                    .into(icImage)
                icHeart.visibility = if (item.favorite == ShopItem.Favorite.FAVORITE) View.VISIBLE else View.GONE
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback< ShopItem>() {
        override fun areItemsTheSame(oldItem:  ShopItem, newItem:  ShopItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem:  ShopItem, newItem:  ShopItem): Boolean {
            return oldItem == newItem
        }
    }
}