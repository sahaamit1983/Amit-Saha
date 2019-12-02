package com.amit.saha.ui.fragments

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer

import org.junit.Before
import org.junit.Rule
import org.junit.Test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.annotation.UiThreadTest
import androidx.test.rule.ActivityTestRule

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import org.hamcrest.Matchers
import com.amit.saha.R
import com.amit.saha.ui.DataState
import com.amit.saha.ui.FactsActivity
import com.amit.saha.util.EspressoIdlingResource
import kotlinx.android.synthetic.main.fragment_list.*
import org.hamcrest.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class ListFactFragmentTest {

    @get:Rule
    var mActivityRule: ActivityTestRule<FactsActivity> = object : ActivityTestRule<FactsActivity>(FactsActivity::class.java) {
        override fun afterActivityLaunched() {
            super.afterActivityLaunched()
            IdlingRegistry.getInstance()
                .register(EspressoIdlingResource.mCountingIdlingResource)
            EspressoIdlingResource.increment()
        }
    }

    private var monitor = getInstrumentation().addMonitor(ListFactFragment::class.java.name, null, false)

    @Before
    @UiThreadTest
    fun setUpFragment() {
        val factsHomeFragment: ListFactFragment =
            mActivityRule.activity.supportFragmentManager.fragments[0] as ListFactFragment
        factsHomeFragment.factsViewModel.getCanadaProfile()
            .observe(mActivityRule.activity, Observer {
                when (it) {
                    is DataState.Success -> {
                        EspressoIdlingResource.decrement()
                    }
                    is DataState.Error -> {
                        EspressoIdlingResource.decrement()
                    }
                }
            })
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.mCountingIdlingResource)
    }

    @Test
    fun swipe_Container_Test() {
        onView(withId(R.id.swipe_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test_RecyclerView_NotNull() {
        val fragment = mActivityRule.activity.supportFragmentManager.fragments[0] as ListFactFragment
        val recyclerView = fragment.view?.findViewById(R.id.recycler_view) as View
        assertNotNull(recyclerView)
    }

    @Test
    fun test_RecyclerView_Visible() {
        onView(withId(R.id.recycler_view))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(mActivityRule.activity.window.decorView)
                )
            ).check(matches(isDisplayed()))
    }

    @Test
    fun test_Title_Visible() {
        onView(withText("About Canada")).check(matches(isDisplayed()))
    }

    @Test
    fun test_Not_Displaying() {
        // At first this data is not displaying at screen
        onView(withText("Housing")).check(doesNotExist())
    }

    @Test
    fun recyclerView_ItemCountText() {
        getInstrumentation().waitForMonitorWithTimeout(monitor, 500)
        onView(withId(R.id.recycler_view)).check{ view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            assertEquals(13, recyclerView.adapter?.itemCount)
        }
    }

    @Test
    fun recyclerView_ChildTitle_TextViewTest() {
        onView(withId(R.id.recycler_view)).check{ view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val vh = recyclerView.findViewHolderForAdapterPosition(2)
            assertNotNull(vh)
            val title = vh?.itemView?.findViewById(R.id.title)  as TextView
            assertNotNull(title)
            assertEquals("Transportation", title.text.toString())
        }
    }

    @Test
    fun recyclerView_ChildDescription_TextViewTest() {
        onView(withId(R.id.recycler_view)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val vh = recyclerView.findViewHolderForAdapterPosition(2)
            assertNotNull(vh)
            val description = vh?.itemView?.findViewById(R.id.description) as TextView
            assertNotNull(description)
            assertEquals("It is a well known fact that polar bears are the main mode of transportation in Canada. They consume far less gas and have the added benefit of being difficult to steal.", description.getText().toString())
        }
    }

    @Test
    fun testCaseForRecyclerClick() {
        onView(withId(R.id.recycler_view))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(mActivityRule.activity.window.decorView)
                )
            )
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
    }

    @Test
    fun testCaseForRecyclerScroll() {
        // Get total item of RecyclerView
        val recyclerView = mActivityRule.activity.recycler_view
        val itemCount = recyclerView.adapter!!.itemCount

        // Scroll to end of page with position
        onView(withId(R.id.recycler_view))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(mActivityRule.activity.window.decorView)
                )
            )
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemCount - 1))
    }

    @Test
    fun testCaseForRecyclerItemViewTitle() {
        //Check title
        val textView = onView(
            allOf(
                withId(R.id.title),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))
    }

    @Test
    fun testCaseForRecyclerItemViewDescription() {
        //Check description
        val textViewDescription = onView(
            allOf(
                withId(R.id.description),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textViewDescription.check(matches(isDisplayed()))
    }

    @Test
    fun testCaseForRecyclerItemViewDescriptionImage() {
        //Check fact image
        val imageViewFacts = onView(
            allOf(
                withId(R.id.description_image), withContentDescription("Description image"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        imageViewFacts.check(matches(isDisplayed()))
    }

    @Test
    fun testCaseForRecyclerItemViewArrow() {
        //Check right arrow image
        val imageViewRightArrow = onView(
            allOf(
                withId(R.id.right_arrow), withContentDescription("Right arrow"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        imageViewRightArrow.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}