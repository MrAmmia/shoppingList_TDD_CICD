package net.thebookofcode.shoppinglistitemswithtdd.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.bumptech.glide.RequestManager
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import launchFragmentInHiltContainer
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.adapters.ShoppingItemAdapter
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItem
import net.thebookofcode.shoppinglistitemswithtdd.getOrAwaitValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class ShoppingFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var navController: TestNavHostController

    @Inject
    lateinit var fragmentFactory: TestShoppingFragmentFactory

    @Inject
    lateinit var glide: RequestManager

    @Before
    fun setUp() {
        hiltRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            //viewModel = testViewModel
        }
        onView(withId(R.id.fabAddShoppingItem)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.addShoppingItemFragment)
    }


    @Test
    fun swipeShoppingItem_deleteItemInDb() {
        val shoppingItem = ShoppingItem("TEST", 1, 1f, "TEST", 1)
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel.insertShoppingItemIntoDb(shoppingItem)
        }
        onView(withId(R.id.rvShoppingItems)).perform(
            actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            assertThat(viewModel.shoppingItems.getOrAwaitValue()).isEmpty()
        }
    }
}