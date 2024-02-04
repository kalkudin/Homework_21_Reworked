package com.example.homework_21_reworked.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework_21_reworked.data.common.Resource
import com.example.homework_21_reworked.presentation.event.ShopEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import com.example.homework_21_reworked.data.common.ErrorType
import com.example.homework_21_reworked.domain.usecase.GetAllShopItemsUseCase
import com.example.homework_21_reworked.domain.usecase.GetFilteredShopItemsUseCase
import com.example.homework_21_reworked.domain.usecase.GetShopItemsUseCase
import com.example.homework_21_reworked.presentation.mapper.shopItemToState
import com.example.homework_21_reworked.presentation.model.CategoryItem
import com.example.homework_21_reworked.presentation.model.ShopItem
import com.example.homework_21_reworked.presentation.model.ShopItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getShopItemsUseCase: GetShopItemsUseCase,
    private val getFilteredShopItemsUseCase: GetFilteredShopItemsUseCase,
    private val getAllShopItemsUseCase: GetAllShopItemsUseCase,
) : ViewModel() {

    private val _shopItemState = MutableStateFlow(ShopItemState())
    val shopItemState : StateFlow<ShopItemState> = _shopItemState.asStateFlow()

    private val _categoryItems = MutableStateFlow<List<CategoryItem>>(emptyList())
    val categoryItems: StateFlow<List<CategoryItem>> = _categoryItems.asStateFlow()

    fun onEvent(event: ShopEvent) {
        when (event) {
            is ShopEvent.GetShopItems -> getShopItems()
            is ShopEvent.GetShopItemsByCategory -> getShopItemsByCategory(event.category)
            is ShopEvent.GetAllItems -> getAllItems()
        }
    }

    private fun getShopItems() {
        Log.d("ShopViewModel", "Fetching shop items")
        viewModelScope.launch {
            getShopItemsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("ShopViewModel", "Successfully retrieved shop items")
                        val items = result.data.map { shopItemToState(it) }
                        _shopItemState.update { currentState ->
                            currentState.copy(isSuccess = items, isLoading = false)
                        }
                        updateCategories(items)
                    }
                    is Resource.Error -> {
                        val errorMessage = mapErrorToMessage(result.errorType)
                        Log.e("ShopViewModel", "Error fetching shop items: $errorMessage")
                        _shopItemState.update { currentState ->
                            currentState.copy(isError = errorMessage, isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        Log.d("ShopViewModel", "Loading shop items")
                        _shopItemState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun getShopItemsByCategory(category: String) {
        Log.d("ShopViewModel", "Fetching shop items by category: $category")
        viewModelScope.launch {
            getFilteredShopItemsUseCase(category).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("ShopViewModel", "Successfully retrieved filtered shop items")
                        val filteredItems = result.data.map { shopItemToState(it) }
                        _shopItemState.update { currentState ->
                            currentState.copy(isSuccess = filteredItems, isLoading = false)
                        }
                    }
                    is Resource.Error -> {
                        val errorMessage = mapErrorToMessage(result.errorType)
                        Log.e("ShopViewModel", "Error fetching filtered shop items: $errorMessage")
                        _shopItemState.update { currentState ->
                            currentState.copy(isError = errorMessage, isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        Log.d("ShopViewModel", "Loading filtered shop items")
                        _shopItemState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun getAllItems() {
        viewModelScope.launch {
            viewModelScope.launch {
                getAllShopItemsUseCase().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Log.d("ShopViewModel", "Successfully retrieved shop items")
                            val items = result.data.map { shopItemToState(it) }
                            _shopItemState.update { currentState ->
                                currentState.copy(isSuccess = items, isLoading = false)
                            }
                            updateCategories(items)
                        }
                        is Resource.Error -> {
                            val errorMessage = mapErrorToMessage(result.errorType)
                            Log.e("ShopViewModel", "Error fetching shop items: $errorMessage")
                            _shopItemState.update { currentState ->
                                currentState.copy(isError = errorMessage, isLoading = false)
                            }
                        }
                        is Resource.Loading -> {
                            Log.d("ShopViewModel", "Loading shop items")
                            _shopItemState.update { currentState ->
                                currentState.copy(isLoading = true)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateCategories(items: List<ShopItem>) {
        val allCategory = listOf(CategoryItem(id = -1, category = "All"))
        val uniqueCategories = items.map { it.category }.distinct().sorted()
        val categoryItems = uniqueCategories.mapIndexed { index, category ->
            CategoryItem(id = index, category = category)
        }
        _categoryItems.value = allCategory + categoryItems
    }

    private fun mapErrorToMessage(errorType: ErrorType): String {
        return when (errorType) {
            is ErrorType.NetworkError -> "Network error"
            is ErrorType.AuthenticationError -> "Authentication failed"
            is ErrorType.NotFound -> "Items not found"
            is ErrorType.AccessDenied -> "Access denied"
            is ErrorType.ServiceUnavailable -> "Service unavailable"
            is ErrorType.UnknownError -> "Unknown error: ${errorType.errorMessage}"
            is ErrorType.EmptyDatabase -> "Data Base Empty"
        }
    }
}