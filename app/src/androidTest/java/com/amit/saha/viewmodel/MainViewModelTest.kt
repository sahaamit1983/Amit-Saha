package com.amit.saha.viewmodel

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amit.saha.repository.Repository
import com.amit.saha.ui.DataState
import com.amit.saha.ui.FactsViewModel
import com.amit.saha.util.LiveDataTestUtils
import com.amit.saha.util.MockTestUtil

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private var factsViewModel: FactsViewModel? = null

    @Mock
    private var repository: Repository? = null

    @Mock
    private val observer: Observer<DataState>? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        factsViewModel = FactsViewModel(repository!!)
    }

    @Test
    @Throws(Exception::class)
    fun observeEmptyRow() {

        //Arrange
        val liveDataTestUtils = LiveDataTestUtils<DataState>()

        //Act
        val resource = liveDataTestUtils.getValue(factsViewModel!!.observeFilteredLiveData())

        //Assert
        Assert.assertNull(resource)
    }

    @Test
    @Throws(Exception::class)
    fun observeRow_whenSetInViewModel() {

        //Arrange
        val facts = MockTestUtil.mockFactResponse()
        val liveDataTestUtils = LiveDataTestUtils<DataState>()

        //Act
        factsViewModel?.setFilteredViewModel(DataState.Success(facts))
        val resource = (liveDataTestUtils.getValue(factsViewModel!!.observeFilteredLiveData()) as DataState.Success).factsDataState

        //Assert
        Assert.assertNotNull(resource)
        Assert.assertNotNull(resource?.title)
        Assert.assertNotNull(resource?.rows)
        Assert.assertNotNull(resource?.rows?.size)
        Assert.assertNotNull(resource?.rows?.get(0))
        Assert.assertNotNull(resource?.rows?.get(0)?.title)
        Assert.assertNotNull(resource?.rows?.get(0)?.description)
        Assert.assertNotNull(resource?.rows?.get(0)?.imageHref)
        Assert.assertNotNull(facts)

        Assert.assertEquals(resource, facts)
        Assert.assertEquals(resource?.title, facts.title)
        Assert.assertEquals(resource?.rows?.size, facts.rows.size)
        Assert.assertEquals(resource?.rows?.get(0)?.title, facts.rows[0].title)
        Assert.assertEquals(resource?.rows?.get(0)?.description, facts.rows[0].description)
        Assert.assertEquals(resource?.rows?.get(0)?.imageHref, facts.rows[0].imageHref)
    }

    @Test
    fun observerFromRepository() {
        factsViewModel!!.observeFilteredLiveData().observeForever(observer!!)
    }

}
