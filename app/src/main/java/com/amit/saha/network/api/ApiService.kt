package com.amit.saha.network.api


import com.amit.saha.model.Facts
import com.amit.saha.util.Constant
import retrofit2.http.GET

interface ApiService {

    @GET(Constant.FACT_INFO)
    suspend fun factData(): Facts
}
