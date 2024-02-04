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
import com.example.homework_21_reworked.domain.usecase.GetShopItemsUseCase
import com.example.homework_21_reworked.presentation.mapper.shopItemToState
import com.example.homework_21_reworked.presentation.model.ShopItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val getShopItemsUseCase: GetShopItemsUseCase
) : ViewModel() {

    private val _shopItemState = MutableStateFlow(ShopItemState())
    val shopItemState : StateFlow<ShopItemState> = _shopItemState.asStateFlow()

    fun onEvent(event: ShopEvent) {
        when (event) {
            is ShopEvent.GetShopItems -> getShopItems()
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