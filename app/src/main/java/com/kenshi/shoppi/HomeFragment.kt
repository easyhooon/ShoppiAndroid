package com.kenshi.shoppi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kenshi.shoppi.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEnterProductDetail.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
                transaction.add(R.id.container_main, ProductDetailFragment())
                transaction.commit()

//            val transaction = parentFragmentManager.beginTransaction().apply {
//                add(R.id.container_main, ProductDetailFragment())
//                commit()
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}