package com.amit.saha.ui

import com.amit.saha.model.Facts

sealed class DataState {

    data class Success(val factsDataState: Facts?) : DataState()

    data class Error(val error: String) : DataState()

    object Loading : DataState()
}