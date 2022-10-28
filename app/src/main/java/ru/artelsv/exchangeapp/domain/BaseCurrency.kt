package ru.artelsv.exchangeapp.domain

import ru.artelsv.exchangeapp.data.model.Currency

sealed class BaseCurrency {

    object EmptyCurrency : BaseCurrency()
    data class Currency(
        val name: String,
        val value: Double = 0.0,
        var favourite: Boolean = false
    ) : BaseCurrency()
}

fun BaseCurrency.Currency.toModel() = Currency(name, value, favourite)

@JvmName("toModelListStringString")
fun Map<String, String>.toModelList(): ArrayList<BaseCurrency.Currency> {
    val list = arrayListOf<BaseCurrency.Currency>()
    forEach { item ->
        list.add(BaseCurrency.Currency(item.key))
    }
    return list
}

@JvmName("toModelListStringDouble")
fun Map<String, Double>.toModelList(): ArrayList<BaseCurrency.Currency> {
    val list = arrayListOf<BaseCurrency.Currency>()
    forEach { item ->
        list.add(BaseCurrency.Currency(item.key, item.value))
    }
    return list
}