package ru.artelsv.exchangeapp.domain.interactor.impl

import kotlinx.coroutines.flow.Flow
import ru.artelsv.exchangeapp.domain.repository.ExchangeRepository
import ru.artelsv.exchangeapp.domain.interactor.ExchangeInteractor
import ru.artelsv.exchangeapp.domain.model.BaseCurrency
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