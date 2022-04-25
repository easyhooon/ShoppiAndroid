package com.kenshi.shoppi.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.shoppi.data.model.CartHeader
import com.kenshi.shoppi.data.model.CartItem
import com.kenshi.shoppi.data.model.CartProduct
import com.kenshi.shoppi.databinding.ItemCartSectionBinding
import com.kenshi.shoppi.databinding.ItemCartSectionHeaderBinding

//2종류의 viewHolder type 을 만들어야 하기 때문에 recyclerview viewHolder 원형 type 을 전달
//하나의 어댑터에서 두개의 아이템뷰홀더를 관리하기 위해 두 아이템 뷰홀더의 타입을 갖게 맞춰줄(묶어줄) 필요가 있음(CartProduct Sealed Class 추가)

private const val VIEW_TYPE_HEADER = 0
private const val VIEW_TYPE_ITEM = 1

class CartAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val cartProducts = mutableListOf<CartProduct>()

    //viewHolder 에는 view 를 전달해줘야
    class HeaderViewHolder(private val binding: ItemCartSectionHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(header: CartHeader) {
            binding.header = header
            // 바로 변경 사항을 업데이트
            binding.executePendingBindings()
        }
    }

    class ItemViewHolder(private val binding: ItemCartSectionBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (cartProducts[position]) {
            is CartHeader -> VIEW_TYPE_HEADER
            is CartItem -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //when 문은 else 를 강제함
        return when(viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(ItemCartSectionHeaderBinding.inflate(inflater, parent, false))
            else -> ItemViewHolder(ItemCartSectionBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                val item = cartProducts[position] as CartHeader
                holder.bind(item)
            }
            is ItemViewHolder -> {
                val item = cartProducts[position] as CartItem
                holder.bind(item)
            }
        }
    }

    //listAdapter 는 이 기능을 대신 구현해줌
    override fun getItemCount(): Int = cartProducts.size

    fun submitHeaderAndItemList(items: List<CartItem>) {
        // groupBy -> key 로 사용할 값을 전달하면 이 key 에 해당되는 목록을 value 에 추가해준 data 를 반환
        val itemGroups = items.groupBy { it.brandName }
        val products = mutableListOf<CartProduct>()
        itemGroups.entries.forEach { entry ->
            val header = CartHeader(entry.key)
            products.add(header)
            products.addAll(entry.value)
        }
        // viewModel 에서 업데이트된 데이터가 cartFragment 를 통해서 adapter 에 전달
        // -> adapter 에서 관리하는 cartProducts 에 추가
        cartProducts.addAll(products)
        //시작되는 값, 새롭게 추가되는 item 개수
        notifyItemRangeInserted(cartProducts.size, products.size)
    }
}