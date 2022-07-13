package com.mobile.app.filterlist.presentation.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.app.filterlist.domain.usecase.ManageCurrencyFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FilterScreenViewModel @Inject constructor(
    private val manageCurrencyFilter: ManageCurrencyFilter
) : ViewModel() {

    private val _stateFlow: MutableLiveData<FilterState> = MutableLiveData()
    val stateFlow: LiveData<FilterState> = _stateFlow

    private val _filterState: MutableLiveData<String> = MutableLiveData()
    val filterState: LiveData<String> = _filterState


    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _stateFlow.postValue(FilterState.Loading(true))
            manageCurrencyFilter.getCurrencyList()
                .enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        val string = response.body() ?: ""
                        val list = manageCurrencyFilter.convertStringToList(string)
                        Log.e("Count Items: ", list.size.toString())

                        if (response.isSuccessful){
                            _stateFlow.postValue(FilterState.Loading(false))
                            _stateFlow.postValue(FilterState.Success(list = list))
                        }
                    }
                    override fun onFailure(call: Call<String>, error: Throwable) {
                        Log.e("error: ", error.stackTraceToString())
                        _stateFlow.postValue(FilterState.Error(errorMessage = "Can't get the data. Please try again"))
                    }
                })
        }
    }

    fun onFilterCurrency(keyword: String) {
        _filterState.value = keyword
        val newFilterList = manageCurrencyFilter.filterList(keyword)
        _stateFlow.postValue(
            FilterState.Success(list = newFilterList)
        )
    }
}