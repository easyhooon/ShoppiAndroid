package com.kenshi.shoppi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.kenshi.shoppi.KEY_PRODUCT_ID
import com.kenshi.shoppi.R
import com.kenshi.shoppi.databinding.FragmentHomeBinding
import com.kenshi.shoppi.ui.common.*

//onProductClick 함수를 호출하는 adapter 를 가지고 있는 Fragment가 HomeFragment 이므로
//HomeFragment 에서 onProductClick 를 정의
//ProductClickListener 를 상속받음
class HomeFragment : Fragment(), ProductClickListener {

    //
    // HomeViewModel 에 생성자가 추가되었기 때문에(repository) 따로 정의 필요
    // 생성자가 추가된 HomeViewModel 은 viewModels 의 2번째 인자로 생성하는 방법을 알려줄 수 있음

    //    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    //    noinline factoryProducer: (() -> Factory)? = null

    //  private val viewModel: HomeViewModel by viewModels {}


    //ViewModelFactory 클래스를 만들어줌줌
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

        //lifecyclerOwner: lifecycler 이 변경되는 것에 대한 알림을 받아
        //현재의 lifecycle 상태를 알고있는 객체를 의미

        //한 가지 필수적으로 추가해야할 것
        //binding 의 lifecycleOwner 를 fragment 으 viewLifeCycleOwner 로 할당
        binding.lifecycleOwner = viewLifecycleOwner

        setToolbar()
        setNavigation()
        setTopBanners()
        setListAdapter()
    }

    private fun setToolbar() {
//            SAM
//            viewModel.title.observe(viewLifecycleOwner, object : Observer<Title> {
//                override fun onChanged(t: Title?) {
//
//                }
//            })

        // ViewModel 의 title 데이터가 업데이트 되어야지만 title 객체가 할당이 됨, NPE
        // title이 null 이 아닐때만 image를 로드하도록 bindingAdapter 코드 수정
        viewModel.title.observe(viewLifecycleOwner) { title ->
            //
            binding.title = title
        }
    }

    private fun setTopBanners() {
//        아래와 같은 표현식
//        binding.vpHomeBanner.adapter = HomeBannerAdapter().apply {
//            viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
//                submitList(banners)
//            })
//        }

        with(binding.vpHomeBanner) {
            adapter = HomeBannerAdapter(viewModel).apply {
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

    private fun setNavigation() {
        viewModel.openProductDetailEvent.observe(viewLifecycleOwner, EventObserver { productId ->
            openProductDetail(productId)
        })
    }

    private fun setListAdapter() {
        // adapter 재사용 (title 만 보여주는 adapter)
        val titleAdapter = SectionTitleAdapter()
        // categoryPromotion adapter 재사용
        val promotionAdapter = ProductPromotionAdapter(this)
        binding.rvHome.adapter = ConcatAdapter(titleAdapter, promotionAdapter)
        viewModel.promotions.observe(viewLifecycleOwner) { promotions ->
            titleAdapter.submitList(listOf(promotions.title))
            promotionAdapter.submitList(promotions.items)
        }
    }

    private fun openProductDetail(productId: String) {
        findNavController().navigate(
            //data 는 bundle 객체에 담아 보냄
            R.id.action_home_to_product_detail, bundleOf(
                //"key" to "value"
                KEY_PRODUCT_ID to productId,
            )
        )
    }

    // ProductClickListener
    override fun onProductClick(productId: String) {
        // 상품 상세 화면으로 이동
        findNavController().navigate(R.id.action_home_to_product_detail, bundleOf(
            // 상품 상세화면 데이터가 구성되어 있지 않기 때문에 string 값을 전달
            KEY_PRODUCT_ID to "desk-1"
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}