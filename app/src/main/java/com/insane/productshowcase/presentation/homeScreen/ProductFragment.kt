package com.insane.productshowcase.presentation.homeScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.insane.productshowcase.data.models.ResponseItem
import com.insane.productshowcase.databinding.FragmentProductBinding
import com.insane.productshowcase.presentation.adapters.DynamicListAdapter
import com.insane.productshowcase.presentation.utils.UiState
import com.insane.productshowcase.presentation.utils.hide
import com.insane.productshowcase.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            productViewModel.productListFlow.collect { uiState ->
                when (uiState) {
                    is UiState.Loading -> displayLoadingState()
                    is UiState.Error -> displayErrorState()
                    is UiState.Success<*> -> {
                        displaySuccessState()
                        inflateData(uiState.content as? List<ResponseItem>)
                    }
                }
            }
        }
    }

    private fun inflateData(productData: List<ResponseItem>?) {
        productData?.let {
            binding.productListLayout.rvProductsMainScreen.apply {
                adapter = DynamicListAdapter()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        } ?: run { displayErrorState() }
    }

    private fun displayErrorState() {
        binding.apply {
            productListLayout.clProductFragment.hide()
            loadingLayout.clDetailsLoading.hide()
            layoutError.clErrorMain.show()
        }
    }

    private fun displayLoadingState() {
        binding.apply {
            productListLayout.clProductFragment.hide()
            loadingLayout.clDetailsLoading.show()
            layoutError.clErrorMain.hide()
        }
    }

    private fun displaySuccessState() {
        binding.apply {
            productListLayout.clProductFragment.show()
            loadingLayout.clDetailsLoading.hide()
            layoutError.clErrorMain.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}