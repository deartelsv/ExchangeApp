package ru.artelsv.exchangeapp.data

import kotlinx.coroutines.flow.*
import ru.artelsv.exchangeapp.data.datasource.ExchangeDataSource
import ru.artelsv.exchangeapp.domain.BaseCurrency
import ru.artelsv.exchangeapp.domain.ExchangeRepository
import ru.artelsv.exchangeapp.domain.toModel
import ru.artelsv.exchangeapp.domain.toModelList
import javax.inject.Inject
import javax.inject.Named

class ExchangeRepositoryImpl @Inject constructor(
    @Named("remote")
    private val remote: ExchangeDataSource,
    @Named("local")
    private val local: ExchangeDataSource
) : ExchangeRepository {

    private var lastList: List<BaseCurrency.Currency> = emptyList()

    override fun getList(
        currency: BaseCurrency.Currency?
    ): Flow<List<BaseCurrency.Currency>> {

        return when (currency) {
            null -> {
                remote.getList().map { it.toModelList() }.combine(local.getFavouriteList()) { list, favList ->
                    val newList = arrayListOf<BaseCurrency.Currency>()
                    newList.addAll(list)

                    favList.forEach { item ->
                        val id = newList.indexOfFirst { it.name == item.name }
                        if (id != -1) {
                            newList[id].favourite = item.favourite
                        }
                    }

                    newList
                }
            }
            else -> {
                remote.getListByCurrency(currency, lastList).map { it.toModelList() }.combine(local.getFavouriteList()) { list, favList ->
                    val newList = arrayListOf<BaseCurrency.Currency>()
                    newList.addAll(list)

                    favList.forEach { item ->
                        val id = newList.indexOfFirst { it.name == item.name }
                        if (id != -1) {
                            newList[id].favourite = item.favourite
                        }
                    }

                    newList
                }
            }
        }
    }


    override fun getFavouriteList(): Flow<List<BaseCurrency.Currency>> =
        local.getFavouriteList()

    override suspend fun setFavourite(currency: BaseCurrency.Currency) {
        local.setFavourite(currency.toModel())
    }
}