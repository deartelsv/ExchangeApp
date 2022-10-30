package ru.artelsv.exchangeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.artelsv.exchangeapp.domain.interactor.ExchangeInteractor
import ru.artelsv.exchangeapp.domain.model.BaseCurrency
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val interactor: ExchangeInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<FavouriteScreenState>(FavouriteScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        updateState()
    }

    fun like(currency: BaseCurrency.Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.setFavourite(currency)
            updateState()
        }
    }

    fun sort(sort: Sort) {
        val state = (state.value as? FavouriteScreenState.DataLoaded) ?: return

        val newList = when (sort) {
            is Sort.Asc -> state.values.sortedBy { item -> item.name }
            is Sort.Desc -> state.values.sortedByDescending { item -> item.name }
            else -> state.values
        }

        _state.value = FavouriteScreenState.DataLoaded(newList)
    }


    private fun updateState() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getFavouriteList()
                .catch {
                    _state.value = FavouriteScreenState.Error
                }
                .collect {
                    _state.value = FavouriteScreenState.DataLoaded(it)
                }
        }
    }
}

sealed class FavouriteScreenState {

    object Loading : FavouriteScreenState()
    object Error : FavouriteScreenState()
    data class DataLoaded(val values: List<BaseCurrency.Currency>) : FavouriteScreenState()
}