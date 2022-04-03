package com.kenshi.shoppi.ui.category

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.databinding.ItemCategoryBinding

//ctrl+좌클 해보면 알겠지만 recyclerViewAdapter 상속해서 만든애임
//ListAdapter 의 문법상 data type 과 ViewHolder 를 명시해놓고 들어가기때문에 생성자로 list를 안받아도 되는 듯
class CategoryAdapter(private val viewModel : CategoryViewModel): ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    //생성자의 viewModel 참조를 위한 inner class 선언
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            //binding 에 viewModel 를 할당해줘야함
            binding.viewModel = viewModel
            binding.category = category
            // 해당 코드를 붙혀줘야 binding 된 데이터가 바로 반영이 됨
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.categoryId == newItem.categoryId

    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem

    }
}