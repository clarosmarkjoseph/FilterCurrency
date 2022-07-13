package com.mobile.app.filterlist.domain.usecase

import com.mobile.app.filterlist.domain.repository.GetCurrencyFilter
import retrofit2.Call
import java.util.*
import javax.inject.Inject

class ManageCurrencyFilter @Inject constructor(
    private val getCurrencyFilter: GetCurrencyFilter
) {

    private var currencyList = emptyList<String>()

    fun getCurrencyList(): Call<String> {
        return getCurrencyFilter.getCurrency()
    }

    fun convertStringToList(value: String): List<String> {
        //split string to line separator /n
        currencyList = value.split(System.lineSeparator()).filter { it.isNotEmpty() }
        return currencyList
    }

    fun filterList(keyword: String): List<String> {
        return if (keyword.isNotEmpty()) {
            currencyList.filter {
                it.lowercase(Locale.ENGLISH).contains(
                    keyword.lowercase(
                        Locale.ENGLISH
                    )
                )
            }
        } else {
            currencyList
        }
    }
}