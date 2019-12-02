package com.amit.saha.api

import com.amit.saha.ui.Resource
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ApiResponseTest {

    @Test
    fun exception() {
        val exception = Exception("test")
        val apiResponse = Resource.error(exception.message!!, exception)
        Assert.assertEquals("test", apiResponse.message)
        Assert.assertEquals(Resource.Status.ERROR, apiResponse.status)
    }

    @Test
    fun success() {
        val resource = Resource.success("test")
        Assert.assertEquals("test", resource.data)
        Assert.assertEquals(Resource.Status.SUCCESS, resource.status)
    }
}
