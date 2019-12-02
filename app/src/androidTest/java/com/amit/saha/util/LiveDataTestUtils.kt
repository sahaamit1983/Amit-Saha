package com.amit.saha.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class LiveDataTestUtils<T> {

    @Throws(InterruptedException::class)
    fun getValue(liveData: LiveData<T>): T? {

        val data = ArrayList<T>()

        //Latch for blocking thread until data is set
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T) {
                data.add(t)
                latch.countDown() //release the latch
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        try {
            latch.await(2, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            throw InterruptedException("Latch failure")
        }

        return if (data.size > 0) {
            data[0]
        } else null
    }
}
