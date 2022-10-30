package ru.artelsv.exchangeapp.data.datasource.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import ru.artelsv.exchangeapp.data.datasource.ExchangeDataSource
import ru.artelsv.exchangeapp.data.model.Currency
import ru.artelsv.exchangeapp.data.service.ExchangeService
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.utils.take
import javax.inject.Inject

class ExchangeRemoteDataSource @Inject constructor(
    private val service: ExchangeService
) : ExchangeDataSource {
    override fun getList(): Flow<Map<String, String>> = flow {
        emit(service.getSymbolsList().symbols.take(20))
    }

    override fun getListByCurrency(
        currency: BaseCurrency.Currency,
        to: List<BaseCurrency.Currency>
    ): Flow<Map<String, Double>> = flow {
        emit(service.getConvertList(
            to.map { it.name }.toNumericalString(),
            currency.name
        ).rates)
    }

    override fun getFavouriteList(): Flow<List<BaseCurrency.Currency>> = emptyFlow()

    override suspend fun setFavourite(currency: Currency) { }
}

fun <T> List<T>.toNumericalString(splitChar: String = ",") = toString().replace(" ", splitChar).replace("[","").replace("]", "")