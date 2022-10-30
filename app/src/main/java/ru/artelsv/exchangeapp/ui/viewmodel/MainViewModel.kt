package ru.artelsv.exchangeapp.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.artelsv.exchangeapp.R
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.domain.ExchangeInteractor
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: ExchangeInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state = _state.asStateFlow()

    private var sort: Sort = Sort.Asc

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
        this.sort = sort
        _state.value = MainScreenState.DataLoaded(state.values.sort(sort), state.selected)
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
                .onStart { _state.value = MainScreenState.Loading }
                .catch {
                    _state.value = MainScreenState.Error
                }
                .collect {
                    _state.value = MainScreenState.DataLoaded(it.sort(sort), currency)
                }
        }
    }
}

fun List<BaseCurrency.Currency>.sort(sort: Sort) = when (sort) {
    is Sort.Asc -> sortedBy { it.name }
    is Sort.Desc -> sortedByDescending { it.name }
    is Sort.AscValue -> sortedBy { it.value }
    is Sort.DescValue -> sortedByDescending { it.value }
}

sealed class MainScreenState {

    object Loading : MainScreenState()
    object Error : MainScreenState()
    data class DataLoaded(
        val values: List<BaseCurrency.Currency>,
        val selected: BaseCurrency = BaseCurrency.EmptyCurrency
    ) : MainScreenState()
}

sealed class Sort(
    @StringRes val textRes: Int
) {

    object Asc : Sort(R.string.main_sort_asc)
    object Desc : Sort(R.string.main_sort_desc)
    object AscValue : Sort(R.string.main_sort_asc_value)
    object DescValue : Sort(R.string.main_sort_desc_value)
}