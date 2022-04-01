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
import java.text.DecimalFormat
import kotlin.math.roundToInt

// T: 레이아웃에 표현할 데이터의 타입, VH: 뷰홀더
// ListAdapter : data 의 list 를 받아서 0번째 부터 순차적으로 뷰홀더와 바인딩을 함
// 이때 레이아웃은 그대로 유지한채로 데이터만 업데이트한다면, 성능상의 이점이 있음, 이를 지원하는게 ListAdapter
class HomeBannerAdapter : ListAdapter<Banner, HomeBannerAdapter.HomeBannerViewHolder>(
    BannerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_banner, parent, false)
       return HomeBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    //view HomeBanner 에서 inflate 시킬 레이아웃을 의미
    class HomeBannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ivBanner= view.findViewById<ImageView>(R.id.iv_banner_image)
        private val tvBannerBadge = view.findViewById<TextView>(R.id.tv_banner_badge)
        private val tvBannerTitle = view.findViewById<TextView>(R.id.tv_banner_title)
        private val ivBannerDetailThumbnailImageView = view.findViewById<ImageView>(R.id.iv_banner_detail_thumbnail)
        private val tvBannerDetailBrandLabel = view.findViewById<TextView>(R.id.tv_banner_detail_brand_label)
        private val tvBannerDetailProductLabel = view.findViewById<TextView>(R.id.tv_banner_detail_product_label)
        private val tvBannerDetailProductDiscountRate = view.findViewById<TextView>(R.id.tv_banner_detail_product_discount_rate)
        private val tvBannerDetailProductDiscountPrice = view.findViewById<TextView>(R.id.tv_banner_detail_product_discount_price)
        private val tvBannerDetailProductPrice = view.findViewById<TextView>(R.id.tv_banner_detail_product_price)

        fun bind(banner: Banner) {

            loadImage(banner.backgroundImageUrl, ivBanner)
            tvBannerBadge.text = banner.badge.label
            tvBannerBadge.background = ColorDrawable(Color.parseColor(banner.badge.backgroundColor))
            tvBannerTitle.text = banner.label
            loadImage(banner.productDetail.thumbnailImageUrl, ivBannerDetailThumbnailImageView)
            tvBannerDetailBrandLabel.text = banner.productDetail.brandName
            tvBannerDetailProductLabel.text = banner.productDetail.label
            tvBannerDetailProductDiscountRate.text = "${banner.productDetail.discountRate}%"
            calculateDiscountAmount(tvBannerDetailProductDiscountPrice, banner.productDetail.discountRate, banner.productDetail.price)
            applyPriceFormat(tvBannerDetailProductPrice, banner.productDetail.price)
        }

        private fun calculateDiscountAmount(view: TextView, discountRate: Int, price: Int) {
            val discountPrice = (((100 - discountRate) / 100.0) * price).roundToInt()
            applyPriceFormat(view, discountPrice)
        }

        private fun applyPriceFormat(view: TextView, price: Int) {
            val decimalFormat = DecimalFormat("#,###")
            view.text = decimalFormat.format(price) + "원"
        }


        fun loadImage(urlString: String, imageView: ImageView) {
            //Glide load 함수 확장함수로 뺌
            //viewHolder 의 대한 참조 context -> itemView
            GlideApp.with(itemView)
                .load(urlString)
                .into(imageView)
        }
    }
}

class BannerDiffCallback: DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.productDetail.productId == newItem.productDetail.productId
    }

    //id 도 같으면 이 메소드 호출
    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        // 다른 프로퍼티의 값도 모두 같은지 확인
        return oldItem == newItem
    }
}

