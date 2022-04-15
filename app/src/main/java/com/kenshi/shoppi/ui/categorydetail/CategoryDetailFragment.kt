package com.kenshi.shoppi.ui.categorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.kenshi.shoppi.KEY_CATEGORY_LABEL
import com.kenshi.shoppi.KEY_PRODUCT_ID
import com.kenshi.shoppi.R
import com.kenshi.shoppi.databinding.FragmentCategoryDetailBinding
import com.kenshi.shoppi.ui.common.ProductClickListener
import com.kenshi.shoppi.ui.common.ProductPromotionAdapter
import com.kenshi.shoppi.ui.common.SectionTitleAdapter
import com.kenshi.shoppi.ui.common.ViewModelFactory

// 이 프래그먼트에서도 ProductClickListener 를 상속받아야 함
class CategoryDetailFragment : Fragment(), ProductClickListener {

    private var _binding: FragmentCategoryDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryDetailViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCategoryDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 이 코드의 의미
        binding.lifecycleOwner = viewLifecycleOwner

        //bundle 객체에 접근
        setToolbar()
        setListAdapter()
    }

    private fun setToolbar() {
        val categoryLabel = requireArguments().getString(KEY_CATEGORY_LABEL)
        binding.tbCategoryDetail.title = categoryLabel
    }

    private fun setListAdapter() {
        //adapter 여러개를 연결 - 여러 종류의 viewType 을 하나의 adapter에 배치할 수 있음
        val topSellingSectionAdapter = CategoryTopSellingSectionAdapter()
        val titleAdapter = SectionTitleAdapter()
        val promotionAdapter = ProductPromotionAdapter(this)
        binding.rvCategoryDetailList.adapter =
            ConcatAdapter(topSellingSectionAdapter, titleAdapter, promotionAdapter)
        viewModel.topSelling.observe(viewLifecycleOwner) { topSelling ->
            topSellingSectionAdapter.submitList(listOf(topSelling))
        }

        //promotion UI Update
        viewModel.promotions.observe(viewLifecycleOwner) { promotions ->
            titleAdapter.submitList(listOf(promotions.title))
            promotionAdapter.submitList(promotions.items)
        }
    }

    // ProductClickListener
    override fun onProductClick(productId: String) {
        // 상품 상세 화면으로 이동
//        findNavController().navigate(R.id.action_home_to_product_detail, bundleOf(
//            // 상품 상세화면 데이터가 구성되어 있지 않기 때문에 string 값을 전달
//            KEY_PRODUCT_ID to "desk-1"
//        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}