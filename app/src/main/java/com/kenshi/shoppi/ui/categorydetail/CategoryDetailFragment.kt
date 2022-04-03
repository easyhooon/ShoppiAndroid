package com.kenshi.shoppi.ui.categorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
        val categoryId = requireArguments().getString(KEY_CATEGORY_ID)
        val categoryLabel = requireArguments().getString(KEY_CATEGORY_LABEL)
        binding.tbCategoryDetail.title = categoryLabel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}