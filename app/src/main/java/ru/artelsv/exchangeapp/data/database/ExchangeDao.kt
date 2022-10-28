package ru.artelsv.exchangeapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.artelsv.exchangeapp.data.model.Currency

@Dao
interface ExchangeDao {

    @Query("SELECT * FROM currency")
    fun getList(): List<Currency>

    @Query("SELECT * FROM currency WHERE currency.selected = 1")
    fun getFavourite(): List<Currency>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(currency: Currency)
}