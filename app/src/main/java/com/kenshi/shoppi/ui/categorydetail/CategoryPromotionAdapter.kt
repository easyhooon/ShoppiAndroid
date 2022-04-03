package com.kenshi.shoppi.ui.categorydetail


import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.shoppi.data.model.Product
import com.kenshi.shoppi.databinding.ItemCategoryPromotionBinding

class CategoryPromotionAdapter: ListAdapter<Product, CategoryPromotionAdapter.CategoryPromotionViewHolder>(ProductDiffCallback()) {

    class CategoryPromotionViewHolder(private val binding: ItemCategoryPromotionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryPromotionViewHolder {
        val binding = ItemCategoryPromotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryPromotionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryPromotionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}