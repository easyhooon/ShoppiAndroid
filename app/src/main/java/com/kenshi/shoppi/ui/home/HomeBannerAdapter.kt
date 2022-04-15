package com.kenshi.shoppi.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kenshi.shoppi.GlideApp
import com.kenshi.shoppi.R
import com.kenshi.shoppi.data.model.Banner
import com.kenshi.shoppi.databinding.ItemHomeBannerBinding
import com.kenshi.shoppi.ui.category.CategoryViewModel
import java.text.DecimalFormat
import kotlin.math.roundToInt

// T: 레이아웃에 표현할 데이터의 타입, VH: 뷰홀더
// ListAdapter : data 의 list 를 받아서 0번째 부터 순차적으로 뷰홀더와 바인딩을 함
// 이때 레이아웃은 그대로 유지한채로 데이터만 업데이트한다면, 성능상의 이점이 있음, 이를 지원하는게 ListAdapter
class HomeBannerAdapter(private val viewModel : HomeViewModel) : ListAdapter<Banner, HomeBannerAdapter.HomeBannerViewHolder>(
    BannerDiffCallback()
) {
    private lateinit var binding: ItemHomeBannerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        binding = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return HomeBannerViewHolder(binding.root)
        return HomeBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //view HomeBanner 에서 inflate 시킬 레이아웃을 의미
//    class HomeBannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    //class 내부에서 binding 변수를 사용하려면 private val 처리 해줘야
    inner class HomeBannerViewHolder(private val binding: ItemHomeBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner) {
            binding.banner = banner
            //이 메소드까지 호출해야 바로 데이터가 바인딩이 됨
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}

class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.productDetail.productId == newItem.productDetail.productId
    }

    //id 도 같으면 이 메소드 호출
    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        // 다른 프로퍼티의 값도 모두 같은지 확인
        return oldItem == newItem
    }
}

