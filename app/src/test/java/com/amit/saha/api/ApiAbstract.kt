package com.amit.saha.api

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@RunWith(JUnit4::class)
abstract class ApiAbstract<T> {

    private lateinit var mockWebServer: MockWebServer

    @Before
    @Throws(Exception::class)
    fun mockServer() {
        this.configureMockServer()
    }

    @After
    @Throws(Exception::class)
    fun stopServer() {
        this.stopMockServer()
    }

    open fun configureMockServer(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    open fun stopMockServer() {
        mockWebServer.shutdown()
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockWebServer.enqueue(MockResponse()
        .setResponseCode(responseCode)
        .setBody(getJson(String.format("api-response/%s", fileName))))

    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer?.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clazz)
    }
}
