package ru.artelsv.exchangeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.domain.ExchangeInteractor
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: ExchangeInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getList()
                .catch {
                    _state.value = MainScreenState.Error
                }
                .collect {
                    _state.value = MainScreenState.DataLoaded(it)
                }
        }
    }

    fun sort(sort: Sort) {
        val state = (state.value as? MainScreenState.DataLoaded) ?: return

        val newList = when (sort) {
            is Sort.Asc -> state.values.sortedBy { item -> item.name }
            is Sort.Desc -> state.values.sortedByDescending { item -> item.name }
            is Sort.AscValue -> state.values.sortedBy { item -> item.value }
            is Sort.DescValue -> state.values.sortedByDescending { item -> item.value }
        }

        _state.value = MainScreenState.DataLoaded(newList, state.selected)
    }

    fun setCurrency(currency: BaseCurrency.Currency) {
        updateState(currency)
    }

    fun like(currency: BaseCurrency.Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.setFavourite(currency)
        }
    }

    private fun updateState(currency: BaseCurrency.Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getList(currency)
                .catch {
                    _state.value = MainScreenState.Error
                }
                .collect {
                    _state.value = MainScreenState.DataLoaded(it, currency)
                }
        }
    }
}

sealed class MainScreenState {

    object Loading : MainScreenState()
    object Error : MainScreenState()
    data class DataLoaded(
        val values: List<BaseCurrency.Currency>,
        val selected: BaseCurrency = BaseCurrency.EmptyCurrency
    ) : MainScreenState()
}

sealed class Sort {

    object Asc : Sort()
    object Desc : Sort()
    object AscValue : Sort()
    object DescValue : Sort()
}