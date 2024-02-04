package com.example.homework_21_reworked.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_21_reworked.databinding.ItemCategoryLayoutBinding
import com.example.homework_21_reworked.presentation.model.CategoryItem

class CategoryItemRecyclerAdapter(
    private val onCategoryClicked: (String) -> Unit
) : ListAdapter<CategoryItem, CategoryItemRecyclerAdapter.CategoryItemViewHolder>(CategoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding = ItemCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryItemViewHolder(binding, onCategoryClicked)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryItemViewHolder(
        private val binding: ItemCategoryLayoutBinding,
        private val onCategoryClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryItem: CategoryItem) {
            with(binding.btnCategorySearch) {
                text = categoryItem.category
                setOnClickListener {
                    onCategoryClicked(categoryItem.category)
                }
            }
        }
    }

    object CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }
}