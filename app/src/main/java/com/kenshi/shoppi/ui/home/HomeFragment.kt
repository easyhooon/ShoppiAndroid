package com.kenshi.shoppi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kenshi.shoppi.R
import com.kenshi.shoppi.databinding.FragmentCartBinding
import com.kenshi.shoppi.databinding.FragmentHomeBinding
import com.kenshi.shoppi.ui.common.ViewModelFactory

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireContext()) }

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

        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setTopBanners()
    }

    private fun setToolbar() {
//            SAM
//            viewModel.title.observe(viewLifecycleOwner, object : Observer<Title> {
//                override fun onChanged(t: Title?) {
//
//                }
//            })

        //ViewModel 의 title 데이터가 업데이트 되어야지만 title 객체가 할당이 됨, NPE
        viewModel.title.observe(viewLifecycleOwner) { title ->
            binding.title = title
        }
    }

    private fun setTopBanners() {
//        수정 필요 - topBanners 의 데이터가 변경과 상관없이 어댑터는 초기화 해야함
//        viewModel.topBanners.observe(viewLifecycleOwner) { banners ->
//            binding.vpHomeBanner.adapter = HomeBannerAdapter().apply {
//                submitList(banners)
//            }
//        }

//        아래와 같은 표현식
//        binding.vpHomeBanner.adapter = HomeBannerAdapter().apply {
//            viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
//                submitList(banners)
//            })
//        }

        with(binding.vpHomeBanner) {
            adapter = HomeBannerAdapter().apply {
                viewModel.topBanners.observe(viewLifecycleOwner) { banners ->
                    submitList(banners)
                }
            }

            //getDimension : dp to pixel
            //블로그 글과 코드 비교해볼것
            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            val screenWidth = resources.displayMetrics.widthPixels
            val offset = screenWidth - pageWidth - pageMargin

            offscreenPageLimit = 3

            setPageTransformer { page, position ->
                page.translationX = position * -offset
            }

            TabLayoutMediator(binding.tlHomeBannerViewpagerIndicator, this) { _, _ -> }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}