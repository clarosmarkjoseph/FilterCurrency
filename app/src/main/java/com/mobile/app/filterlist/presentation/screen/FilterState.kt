package com.mobile.app.filterlist.presentation.screen

sealed class FilterState {
    data class Loading(val showProgress: Boolean) : FilterState()
    data class Success(val list: List<String>) : FilterState()
    data class Error(val title: String = "", val errorMessage: String) : FilterState()
}