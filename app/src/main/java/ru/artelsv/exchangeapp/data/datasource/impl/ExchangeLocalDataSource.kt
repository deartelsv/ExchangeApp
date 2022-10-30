package ru.artelsv.exchangeapp.data.datasource.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import ru.artelsv.exchangeapp.data.database.ExchangeDao
import ru.artelsv.exchangeapp.data.datasource.ExchangeDataSource
import ru.artelsv.exchangeapp.data.model.Currency
import ru.artelsv.exchangeapp.data.model.toModel
import ru.artelsv.exchangeapp.domain.model.BaseCurrency
import javax.inject.Inject

class ExchangeLocalDataSource @Inject constructor(
    private val exchangeDao: ExchangeDao
) : ExchangeDataSource {
    override fun getList(): Flow<Map<String, String>> = emptyFlow()

    override fun getListByCurrency(
        currency: BaseCurrency.Currency,
        to: List<BaseCurrency.Currency>
    ): Flow<Map<String, Double>> = emptyFlow()

    override fun getFavouriteList(): Flow<List<BaseCurrency.Currency>> = flow {
        emit(exchangeDao.getFavourite()?.map { it.toModel() } ?: emptyList())
    }

    override suspend fun setFavourite(currency: Currency) {
        exchangeDao.insertFavourite(currency)
    }
}