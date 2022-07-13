package com.mobile.app.filterlist.data.repository

import com.mobile.app.filterlist.data.remote.CurrencyFilterService
import com.mobile.app.filterlist.domain.repository.GetCurrencyFilter
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import javax.inject.Inject

class GetCurrencyFilterImpl @Inject constructor(
    private val currencyFilterService: CurrencyFilterService
) : GetCurrencyFilter {

    override fun getCurrency(): Call<String> {
        return currencyFilterService.getCurrency()
    }
}