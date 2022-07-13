package com.mobile.app.filterlist.domain.repository

import retrofit2.Call

interface GetCurrencyFilter {

    fun getCurrency(): Call<String>
}