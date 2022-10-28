package ru.artelsv.exchangeapp.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangeInteractorImpl @Inject constructor(
    private val repository: ExchangeRepository
) : ExchangeInteractor {

    override fun getList(currency: BaseCurrency.Currency?): Flow<List<BaseCurrency.Currency>> =
        repository.getList(currency)

    override fun getFavouriteList(): Flow<List<BaseCurrency.Currency>> =
        repository.getFavouriteList()

    override suspend fun setFavourite(currency: BaseCurrency.Currency) {
        repository.setFavourite(currency)
    }
}