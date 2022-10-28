package ru.artelsv.exchangeapp.domain

import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {

    fun getList(currency: BaseCurrency.Currency? = null): Flow<List<BaseCurrency.Currency>>
    fun getFavouriteList(): Flow<List<BaseCurrency.Currency>>
    suspend fun setFavourite(currency: BaseCurrency.Currency)
}