package com.amit.saha.repository

import androidx.lifecycle.MutableLiveData
import com.amit.saha.network.api.ApiService
import com.amit.saha.ui.DataState
import com.amit.saha.ui.fragments.adapter.RecyclerAdapter

import javax.inject.Inject
import javax.inject.Singleton

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.OkHttpClient
import okhttp3.Request

@Suppress("NAME_SHADOWING")
@Singleton
open class Repository @Inject constructor(val apiService: ApiService) {

    private var job : CompletableJob? = null
    fun getCanadaProfile() : MutableLiveData<DataState>{

        job = Job()
        return object : MutableLiveData<DataState>() {
            override fun onActive() {
                super.onActive()
                value = DataState.Loading
                job?.let { theJob->
                    CoroutineScope(IO + theJob).launch {
                        try {
                            val factsData = apiService.factData()
                            withContext(Main) {
                                value = DataState.Success(factsData)
                                theJob.complete()
                            }
                        } catch (e : Exception) {
                            withContext(Main) {
                                value = DataState.Error(e.message?:"Error")
                                theJob.complete()
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateImageResponse(factsData: MutableLiveData<DataState>) {
        val job = Job()
        val facts = (factsData.value as DataState.Success).factsDataState

        job.let { theJob->
            CoroutineScope(IO + theJob).launch {
                for(row in facts?.rows!!) {
                    val job = Job()
                    val data = async(IO + job) {
                        try {
                            val okHttpClient = OkHttpClient()
                            val builder = Request.Builder()
                            builder.url(row.imageHref ?: "")
                            val request = builder.build()
                            val response = okHttpClient.newCall(request).execute()
                            response.isSuccessful
                        } catch (e : Exception) {
                          false
                        }
                    }
                    if(!data.await()) {
                        row.itemType = RecyclerAdapter.FactItemWithNoImage
                    }
                    job.complete()
                }
            }.invokeOnCompletion {
                CoroutineScope(Main + job).launch {
                    factsData.value = DataState.Success(facts)
                }
            }
        }
    }

    fun cancelJob() {
        job?.cancel()
    }
}
