package ru.artelsv.exchangeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.domain.ExchangeInteractor
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