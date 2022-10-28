package ru.artelsv.exchangeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.artelsv.exchangeapp.data.model.Currency

@Database(entities = [Currency::class], version = 1)
abstract class ExchangeDatabase : RoomDatabase() {

    abstract fun exchangeDao(): ExchangeDao
}