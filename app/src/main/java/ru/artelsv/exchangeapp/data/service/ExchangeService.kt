package ru.artelsv.exchangeapp.data.service

import retrofit2.http.GET
import retrofit2.http.Query
import ru.artelsv.exchangeapp.data.model.Convert
import ru.artelsv.exchangeapp.data.model.ExchangeResponse
import ru.artelsv.exchangeapp.data.service.Constants.CONVERT_URL
import ru.artelsv.exchangeapp.data.service.Constants.SYMBOLS_LIST_URL

interface ExchangeService {

    @GET(SYMBOLS_LIST_URL)
    suspend fun getSymbolsList() : ExchangeResponse

    @GET(CONVERT_URL)
    suspend fun getConvertList(@Query("symbols") symbols: String, @Query("base") base: String) : Convert
}

object Constants {

    const val BASE_URL = "https://api.apilayer.com/exchangerates_data/"
    const val SYMBOLS_LIST_URL = BASE_URL + "symbols"
    const val CONVERT_URL = BASE_URL + "latest"
}
