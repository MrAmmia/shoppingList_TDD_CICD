package net.thebookofcode.shoppinglistitemswithtdd.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import launchFragmentInHiltContainer
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.adapters.ImageAdapter
import net.thebookofcode.shoppinglistitemswithtdd.getOrAwaitValue
import net.thebookofcode.shoppinglistitemswithtdd.logic.ShoppingViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class ImagePickFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: TestShoppingFragmentFactory

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.nav_graph)
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        var testViewModel: ShoppingViewModel? = null
        val imageUrl = "TEST"
        launchFragmentInHiltContainer<ImagePickFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            navController.setCurrentDestination(R.id.imagePickFragment)
            imageAdapter.images = listOf(imageUrl)
            testViewModel = viewModel
        }
        onView(withId(R.id.rvImages)).perform(
            actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )
        assertThat(testViewModel?.currentImageUrl?.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}