package com.kenshi.shoppi.ui.common


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.shoppi.data.model.Product
import com.kenshi.shoppi.databinding.ItemProductPromotionBinding

// 원래는 viewModel 을 생성자로 넘겨서 viewModel 의 메소드를 통해 화면 이동을 구현하였는데
// 해당 어뎁터를 공통으로 사용하게 되어 viewModel을 넘길 수 없게됨 (AAC ViewModel 은 view 와 1:1 관계)
// 해결: 생성자로 Listener 를 구현
class ProductPromotionAdapter(private val clickListener: ProductClickListener):
    ListAdapter<Product, ProductPromotionAdapter.ProductPromotionViewHolder>(
    ProductDiffCallback()) {

    //clickListener parameter 를 할당하기 위해 inner
    inner class ProductPromotionViewHolder(private val binding: ItemProductPromotionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.clickListener = clickListener
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductPromotionViewHolder {
        val binding = ItemProductPromotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductPromotionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductPromotionViewHolder, position: Int) {
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