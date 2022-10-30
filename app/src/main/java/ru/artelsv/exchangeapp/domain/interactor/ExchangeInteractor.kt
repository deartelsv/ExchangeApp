package ru.artelsv.exchangeapp.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.artelsv.exchangeapp.domain.model.BaseCurrency

interface ExchangeInteractor {
    fun getList(currency: BaseCurrency.Currency? = null): Flow<List<BaseCurrency.Currency>>
    fun getFavouriteList(): Flow<List<BaseCurrency.Currency>>
    suspend fun setFavourite(currency: BaseCurrency.Currency)
}