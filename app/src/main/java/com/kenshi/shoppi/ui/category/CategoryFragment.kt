package com.kenshi.shoppi.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kenshi.shoppi.KEY_CATEGORY_ID
import com.kenshi.shoppi.KEY_CATEGORY_LABEL
import com.kenshi.shoppi.R
import com.kenshi.shoppi.data.model.Category
import com.kenshi.shoppi.databinding.FragmentCategoryBinding
import com.kenshi.shoppi.ui.common.EventObserver
import com.kenshi.shoppi.ui.common.ViewModelFactory
import java.util.*

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = CategoryAdapter(viewModel)
        binding.rvCategoryList.adapter = categoryAdapter
        viewModel.items.observe(viewLifecycleOwner) {
            categoryAdapter.submitList(it)
        }

        // observe 메소드를 통해 상태가 변경됨을 알림을 받음 (상태가 변경되면 아래의 메소드를 호출)
        // categoryFragment 에서 categoryDetailFragment 로 이동을 했다가 back 으로 다시 돌아오지만
        // CategoryFragment는 여전히 LiveData 의 event를 수신하고 있기때문에
        // 핸드폰의 뒤로가기 버튼이 작동하지 않는것 처럼 보임
        // (돌아왔다가 바로 다시 categoryDetailFragment로 이동 -> 계속 머물고 있는것처럼 보임)
        // 이러한 방법을 해결하기 위해 한번 소비한 데이터는 다시 사용할 수 없도록 하는 방법이 존재
//        viewModel.openCategoryEvent.observe(viewLifecycleOwner) {
//            openCategoryDetail(it.categoryId, it.label)
//        }

        viewModel.openCategoryEvent.observe(viewLifecycleOwner, EventObserver { category ->
            openCategoryDetail(category.categoryId, category.label)
        })
    }

    private fun openCategoryDetail(categoryId: String, categoryLabel: String) {
        findNavController().navigate(
            //data 는 bundle 객체에 담아 보냄
            R.id.action_category_to_category_detail, bundleOf(
                //"key" to "value"
                KEY_CATEGORY_ID to categoryId,
                KEY_CATEGORY_LABEL to categoryLabel
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}