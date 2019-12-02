package com.amit.saha.repository

import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.amit.saha.network.api.ApiService
import com.amit.saha.ui.DataState
import com.amit.saha.util.LiveDataTestUtils
import com.amit.saha.util.MockTestUtil
import kotlinx.coroutines.runBlocking

import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    private val position = 3

    @Mock
    private val apiService: ApiService? = null

    @Mock
    private val observer: Observer<DataState>? = null

    private var repository: Repository? = null

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = Repository(apiService!!)
    }

    @Test
    @Throws(Exception::class)
    fun testRepository() {

        //Arrange
        val facts = MockTestUtil.mockFactResponse()
        val liveDataTestUtils = LiveDataTestUtils<DataState>()

        //Act
        val liveData = repository?.getCanadaProfile()
        val resource = (liveDataTestUtils.getValue(liveData!!) as DataState.Success).factsDataState

        //Assert
        Assert.assertNotNull(liveData)
        Assert.assertNotNull(resource)
        Assert.assertNotNull(resource?.title)
        Assert.assertNotNull(resource?.rows)
        Assert.assertNotNull(resource?.rows?.size)
        Assert.assertNotNull(resource?.rows?.get(position))
        Assert.assertNotNull(resource?.rows?.get(position)?.title)
        Assert.assertNotNull(resource?.rows?.get(position)?.description)
        Assert.assertNotNull(resource?.rows?.get(position)?.imageHref)
        Assert.assertNotNull(facts)

        Assert.assertNotEquals(resource, facts)
        Assert.assertEquals(resource?.title, facts.title)
        Assert.assertNotEquals(resource?.rows?.size, facts.rows.size)
        Assert.assertEquals(resource?.rows?.get(position)?.title, facts.rows[0].title)
        Assert.assertEquals(resource?.rows?.get(position)?.description, facts.rows[0].description)
        Assert.assertEquals(resource?.rows?.get(position)?.imageHref, facts.rows[0].imageHref)
    }

    @Test
    fun apiTest() {
        val facts = MockTestUtil.mockFactResponse()
        Assert.assertNotNull(facts)
        runBlocking {
            `when`(apiService?.factData()).thenReturn(facts)
        }
    }

    @Test
    fun observerFromRepository() {
        repository?.getCanadaProfile()?.observeForever(observer!!)
    }

}
