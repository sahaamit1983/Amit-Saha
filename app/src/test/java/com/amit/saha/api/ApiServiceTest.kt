package com.amit.saha.api

import com.amit.saha.network.api.ApiService
import kotlinx.coroutines.runBlocking

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import java.net.HttpURLConnection

class ApiServiceTest : ApiAbstract<ApiService>() {

    private lateinit var service: ApiService

    @Before
    fun initService() {
        service = createService(ApiService::class.java)
        mockHttpResponse("canada_details.json", HttpURLConnection.HTTP_OK)
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactNotNullTest() {
        runBlocking {
            val facts = service.factData()
            Assert.assertNotNull(facts)
        }
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactRowListNotNullTest() {
        runBlocking {
            val rows = service.factData().rows
            Assert.assertNotNull(rows)
        }
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactRowNotNullTest() {
        runBlocking {
            val rows = service.factData().rows[0]
            Assert.assertNotNull(rows)
        }
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactTitleTest() {
        runBlocking {
            val facts = service.factData()
            Assert.assertEquals("About Canada", facts.title)
        }
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactFirstSubTitleTest() {
        runBlocking {
            val facts = service.factData()
            val row = facts.rows[0]
            Assert.assertEquals("Beavers", row.title)
        }
     }

    @Test
    @Throws(Exception::class)
    fun fetchFactFirstSubDescriptionTest() {
        runBlocking {
            val facts = service.factData()
            val row = facts.rows[0]
            Assert.assertEquals("Beavers are second only to humans in their ability to manipulate and change their environment. They can measure up to 1.3 metres long. A group of beavers is called a colony", row?.description)

        }
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactFirstSubImageTest() {
        runBlocking {
            val facts = service.factData()
            val row = facts.rows[0]
            Assert.assertEquals("http://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/American_Beaver.jpg/220px-American_Beaver.jpg", row?.imageHref)

        }
    }

    @Test
    @Throws(Exception::class)
    fun fetchFactRowsListSizeTest() {
        runBlocking {
            val facts = service.factData()
            Assert.assertEquals(14, facts.rows.size)
        }
    }
}
