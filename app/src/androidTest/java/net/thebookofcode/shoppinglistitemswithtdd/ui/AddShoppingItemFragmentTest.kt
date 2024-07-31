package net.thebookofcode.shoppinglistitemswithtdd.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import net.thebookofcode.shoppinglistitemswithtdd.HiltTestActivity
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItem
import net.thebookofcode.shoppinglistitemswithtdd.getOrAwaitValue
import net.thebookofcode.shoppinglistitemswithtdd.logic.ShoppingViewModel
import net.thebookofcode.shoppinglistitemswithtdd.repositories.FakeShoppingRepositoryAndroidTest
import androidx.core.util.Preconditions
import androidx.test.core.app.ActivityScenario.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import launchFragmentInHiltContainer
import net.thebookofcode.shoppinglistitemswithtdd.repositories.ShoppingRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class AddShoppingItemFragmentTest {
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
    fun pressBackButton_popBackStack() {
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            navController.setCurrentDestination(R.id.addShoppingItemFragment)
        }
        pressBack()
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.shoppingFragment)
    }

    @Test
    fun pressBackButton_setsCurrentUrlToEmptyString_returnsTrue() {
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            navController.setCurrentDestination(R.id.addShoppingItemFragment)
            testViewModel = viewModel
        }
        pressBack()
        assertThat(testViewModel?.currentImageUrl?.getOrAwaitValue()).isEmpty()
    }


    @Test
    fun clickOnImageView_navigatesToImagePickFragment() {
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            navController.setCurrentDestination(R.id.addShoppingItemFragment)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.imagePickFragment)
    }


    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        var testViewModel: ShoppingViewModel? = null
        val shoppingItem = ShoppingItem("shopping item", 5, 5.5f, "")
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            testViewModel = viewModel
            navController.setCurrentDestination(R.id.addShoppingItemFragment)
        }
        onView(withId(R.id.etShoppingItemName)).perform(replaceText(shoppingItem.name))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText(shoppingItem.amount.toString()))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText(shoppingItem.price.toString()))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))

    }
}