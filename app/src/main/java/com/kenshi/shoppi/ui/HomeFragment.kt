package com.kenshi.shoppi.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.kenshi.shoppi.AssetLoader
import com.kenshi.shoppi.GlideApp
import com.kenshi.shoppi.R
import com.kenshi.shoppi.data.model.HomeData
import com.kenshi.shoppi.data.model.Title
import com.kenshi.shoppi.databinding.FragmentHomeBinding
import org.json.JSONObject
import java.util.*

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

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

//            SAM
//            viewModel.title.observe(viewLifecycleOwner, object : Observer<Title> {
//                override fun onChanged(t: Title?) {
//
//                }
//            })

            viewModel.title.observe(viewLifecycleOwner) { title ->
                binding.tbHomeTitle.text = title.text

                GlideApp.with(this)
                    .load(title.iconUrl)
                    .centerCrop()
                    .into(binding.tbHomeIcon)
            }

            viewModel.topBanners.observe(viewLifecycleOwner) { banners ->
                binding.vpHomeBanner.adapter = HomeBannerAdapter().apply {
                    submitList(banners)
                }
            }

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