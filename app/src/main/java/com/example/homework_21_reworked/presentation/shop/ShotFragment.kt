package com.example.homework_21_reworked.presentation.shop

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_21_reworked.databinding.FragmentShopLayoutBinding
import com.example.homework_21_reworked.presentation.adapter.CategoryItemRecyclerAdapter
import com.example.homework_21_reworked.presentation.adapter.ShopItemRecyclerAdapter
import com.example.homework_21_reworked.presentation.base.BaseFragment
import com.example.homework_21_reworked.presentation.event.ShopEvent
import com.example.homework_21_reworked.presentation.model.ShopItemState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShotFragment : BaseFragment<FragmentShopLayoutBinding>(FragmentShopLayoutBinding::inflate)  {

    private val shopViewModel : ShopViewModel by viewModels()
    private val shopAdapter: ShopItemRecyclerAdapter by lazy { ShopItemRecyclerAdapter() }
    private lateinit var categoryAdapter: CategoryItemRecyclerAdapter

    override fun bind() {
        bindShopItems()
        bindItemRecyclerView()
        bindCategoryRecyclerView()
    }

    override fun bindViewActionListeners() {

    }

    override fun bindObservers() {
        bindShopFlow()
        bindCategoryFlow()
    }

    private fun bindItemRecyclerView() {
        binding.shopItemRecyclerView.apply {
            adapter = shopAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun bindCategoryRecyclerView() {
        categoryAdapter = CategoryItemRecyclerAdapter { category ->
            handleCategoryClick(category)
        }
        binding.categoryItemRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun bindShopItems() {
        shopViewModel.onEvent(ShopEvent.GetShopItems)
    }

    private fun bindShopFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                shopViewModel.shopItemState.collect { it
                    handleShopState(state = it)
                }
            }
        }
    }

    private fun bindCategoryFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                shopViewModel.categoryItems.collect { categories ->
                    categoryAdapter.submitList(categories)
                }
            }
        }
    }

    private fun handleShopState(state : ShopItemState) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.isSuccess?.let { list ->
            shopAdapter.submitList(list)
        }

        state.isError?.let { errorMessage ->
            showErrorMessage(errorMessage)
        }
    }

    private fun handleCategoryClick(category : String) {
        if (category == "All") {
            shopViewModel.onEvent(ShopEvent.GetShopItems)
        } else {
            shopViewModel.onEvent(ShopEvent.GetShopItemsByCategory(category))
        }
    }

    private fun showErrorMessage(message : String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}