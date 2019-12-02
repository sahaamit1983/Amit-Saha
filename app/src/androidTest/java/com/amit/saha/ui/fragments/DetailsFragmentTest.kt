package com.amit.saha.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.amit.saha.R
import com.amit.saha.ui.FactsActivity
import com.amit.saha.util.MockTestUtil
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
class DetailsFragmentTest {

    @get:Rule
    val mActivityRule = ActivityTestRule(FactsActivity::class.java)

    @Test
    fun test_View_NotNull() {
        val transaction = mActivityRule.activity.supportFragmentManager.beginTransaction()
        val detailsFragment = DetailsFragment()
        val args = Bundle().apply {
            putSerializable(DetailsFragment.ARG_ROW, MockTestUtil.mockRows()[0])
        }
        detailsFragment.arguments = args
        transaction.add(R.id.main_container, detailsFragment, "TAG")
        transaction.commitAllowingStateLoss()
        getInstrumentation().waitForIdleSync()
        val tv = detailsFragment.view?.findViewById(R.id.details_text) as View
        Assert.assertNotNull(tv)
        val im = detailsFragment.view?.findViewById(R.id.deails_image) as View
        Assert.assertNotNull(im)
        onView(withId(tv.id)).check(matches(isDisplayed()))
        onView(withId(tv.id)).check(matches(withText(MockTestUtil.Row1Description)))
        onView(withId(tv.id)).check(matches(withText("These Saturday night CBC broadcasts originally aired on radio in 1931. In 1952 they debuted on television and continue to unite (and divide) the nation each week.")))
    }
}
