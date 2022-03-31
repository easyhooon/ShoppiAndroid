package com.kenshi.shoppi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.kenshi.shoppi.AssetLoader
import com.kenshi.shoppi.GlideApp
import com.kenshi.shoppi.R
import com.kenshi.shoppi.data.model.HomeData
import com.kenshi.shoppi.databinding.FragmentHomeBinding
import org.json.JSONObject

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        //return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val assetLoader = AssetLoader()
        val homeJsonString = assetLoader.getJsonString(requireContext(), "home.json")
        Log.d("homeData", homeJsonString ?: "")

        //gson 라이브러리의 편리함을 알게되었다.
        if (!homeJsonString.isNullOrBlank()) {
            val gson = Gson()
            val homeData = gson.fromJson(homeJsonString, HomeData::class.java)


//            val jsonObject = JSONObject(homeJsonString)
//            val title = jsonObject.getJSONObject("title")
//            val text = title.getString("text")
//            val iconUrl = title.getString("icon_url")

            val text = homeData.title.text
            val iconUrl = homeData.title.iconUrl

            binding.tbHomeTitle.text = text

            GlideApp.with(this)
                .load(iconUrl)
                .centerCrop()
                .into(binding.tbHomeIcon)

            // 직접 구현방법, 이를 대신 해주는 라이브러리 gson
//            val topBanners = jsonObject.getJSONArray("top_banners")
//            val size = topBanners.length()
//            for(index in 0 until size) {
//                val bannerObject = topBanners.getJSONObject(index)
//                val backgroundImageUrl = bannerObject.getString("background_image_url")
//                val badgeObject = bannerObject.getJSONObject("badge")
//                val badgeLabel = badgeObject.getString("label")
//                val badgeBackgroundColor = badgeObject.getString("background_color")
//                val bannerBadge = BannerBadge(badgeLabel, badgeBackgroundColor)
//
//                val banner = Banner(
//                    backgroundImageUrl,
//                    bannerBadge,
//                    bannerLabel,
//                    bannerProductDetail
//                )
//            }

            binding.vpHomeBanner.adapter = HomeBannerAdapter().apply{
                submitList(homeData.topBanners)
            }
            //SAM
            //getDimension : dp to pixel
            //블로그 글과 코드 비교해볼것
            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            val screenWidth = resources.displayMetrics.widthPixels
            val offset = screenWidth - pageWidth - pageMargin

            binding.vpHomeBanner.offscreenPageLimit = 3

            binding.vpHomeBanner.setPageTransformer { page, position ->
                page.translationX = position * -offset
            }

            TabLayoutMediator(binding.tlHomeBannerViewpagerIndicator, binding.vpHomeBanner) { tab, position ->

            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}