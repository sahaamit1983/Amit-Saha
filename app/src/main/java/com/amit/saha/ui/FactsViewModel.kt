package com.amit.saha.ui

import androidx.lifecycle.*
import com.amit.saha.repository.Repository
import com.amit.saha.ui.fragments.adapter.RecyclerAdapter.Companion.FactItemWithNoDescription
import com.amit.saha.ui.fragments.adapter.RecyclerAdapter.Companion.FactItemWithNoImage

import javax.inject.Inject

class FactsViewModel @Inject
constructor(private val repository: Repository) : ViewModel() {

    private var filteredLiveData : MutableLiveData<DataState> = MutableLiveData()

    fun getCanadaProfile() : MutableLiveData<DataState> {
        return repository.getCanadaProfile()
    }

    fun setFilteredViewModel(factsResource : DataState) {
        filteredLiveData.value = filterFactsData(factsResource)
    }

    fun observeFilteredLiveData() : MutableLiveData<DataState> {
        return filteredLiveData
    }

    fun observeListImageVisibility(){
        repository.updateImageResponse(filteredLiveData)
    }

    private fun filterFactsData(factsData : DataState?):DataState?{
        val facts = (factsData as DataState.Success).factsDataState
        val filteredData = facts?.rows?.
            filter {!it.title.isNullOrBlank()}

        filteredData?.forEach { fact ->
            if (fact.description.isNullOrEmpty())
                fact.itemType = FactItemWithNoDescription
            if (fact.imageHref.isNullOrEmpty())
                fact.itemType = FactItemWithNoImage
        }

        facts?.rows = filteredData!!
        return factsData
    }

    fun cancelJob() {
        repository.cancelJob()
    }
}
