package ru.artelsv.exchangeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.artelsv.exchangeapp.domain.BaseCurrency

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey val name: String,
    val value: Double,
    val selected: Boolean = false
)

fun Currency.toModel() = BaseCurrency.Currency(name, value, selected)