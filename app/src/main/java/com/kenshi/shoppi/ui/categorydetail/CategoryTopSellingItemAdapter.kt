package com.kenshi.shoppi.ui.categorydetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.databinding.ItemCategoryTopSellingBinding
import com.kenshi.shoppi.ui.common.CategoryDiffCallback

class CategoryTopSellingItemAdapter: ListAdapter<Category, CategoryTopSellingItemAdapter.TopSellingItemViewHolder>(CategoryDiffCallback()) {

    //viewHolder 는 인자로 view를 전달을 해야하므로 생성자로 binding 클래스를 추가, binding.root 를 전달
    class TopSellingItemViewHolder(private val binding: ItemCategoryTopSellingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopSellingItemViewHolder {
        val binding = ItemCategoryTopSellingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopSellingItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopSellingItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}