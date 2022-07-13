package com.mobile.app.filterlist.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface CurrencyFilterService {

    @Streaming
    @GET("content/worldmate/currencies/currency2008.dat")
    fun getCurrency(): Call<String>

}