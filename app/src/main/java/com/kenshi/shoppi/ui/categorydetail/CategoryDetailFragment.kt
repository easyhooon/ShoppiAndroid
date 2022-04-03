package com.kenshi.shoppi.ui.categorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.kenshi.shoppi.KEY_CATEGORY_ID
import com.kenshi.shoppi.KEY_CATEGORY_LABEL
import com.kenshi.shoppi.databinding.FragmentCategoryDetailBinding
import com.kenshi.shoppi.ui.category.CategoryViewModel
import com.kenshi.shoppi.ui.common.ViewModelFactory

class CategoryDetailFragment : Fragment() {

    private var _binding: FragmentCategoryDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val titleAdapter = CategorySectionTitleAdapter()
        val promotionAdapter = CategoryPromotionAdapter()
        binding.rvCategoryDetailList.adapter = ConcatAdapter(titleAdapter, promotionAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}