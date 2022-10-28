package ru.artelsv.exchangeapp.data.datasource

import kotlinx.coroutines.flow.Flow
import ru.artelsv.exchangeapp.data.model.Currency
import ru.artelsv.exchangeapp.domain.BaseCurrency

interface ExchangeDataSource {

    fun getList(): Flow<Map<String, String>>
    fun getListByCurrency(currency: BaseCurrency.Currency, to: List<BaseCurrency.Currency>): Flow<Map<String, Double>>
    fun getFavouriteList(): Flow<List<BaseCurrency.Currency>>
    suspend fun setFavourite(currency: Currency)
}