package com.amit.saha.ui

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar


import org.junit.Assert
import org.junit.Rule
import org.junit.Test

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.rule.ActivityTestRule

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.amit.saha.R

class MainActivityTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<FactsActivity> = ActivityTestRule(FactsActivity::class.java)
    private val monitor = getInstrumentation().addMonitor(FactsActivity::class.java?.name, null, false)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = getApplicationContext() as Context
        Assert.assertEquals("com.amit.saha.apptest.apptest", appContext.packageName)
        Assert.assertNotEquals("com.sas.dagger.ui", appContext.packageName)
    }

    @Test
    fun progressBarTest() {
        val view = mActivityRule.activity.findViewById(R.id.progress_bar) as View
        Assert.assertNotNull(view)
        val progressBar = view as ProgressBar
        Assert.assertNotNull(progressBar)
    }

    @Test
    fun frameLayoutTest() {
        val view = mActivityRule.activity.findViewById(R.id.main_container) as View
        Assert.assertNotNull(view)
        val frame = view as FrameLayout
        Assert.assertNotNull(frame)
        onView(withId(frame.id)).check(matches(isDisplayed()))
        getInstrumentation().waitForMonitorWithTimeout(monitor, 1000)
    }
}
