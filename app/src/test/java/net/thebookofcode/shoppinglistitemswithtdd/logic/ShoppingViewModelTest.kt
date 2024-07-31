package net.thebookofcode.shoppinglistitemswithtdd.logic

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import net.thebookofcode.shoppinglistitemswithtdd.getOrAwaitValueTest
import net.thebookofcode.shoppinglistitemswithtdd.other.Status
import net.thebookofcode.shoppinglistitemswithtdd.other.Constants.MAX_NAME_LENGTH
import net.thebookofcode.shoppinglistitemswithtdd.other.Constants.MAX_PRICE_LENGTH
import net.thebookofcode.shoppinglistitemswithtdd.repositories.FakeShoppingRepository
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insert shopping item with empty fields returns error`() {
        viewModel.insertShoppingItem("name", "", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name returns error`() {
        val string = buildString {
            for (i in 1..MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem(string, "5", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price returns error`() {
        val string = buildString {
            for (i in 1..MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertShoppingItem("name", "5", string)

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount returns error`() {
        viewModel.insertShoppingItem("name", "999999999999999999999999999999999999999", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid details returns success`() {

        viewModel.insertShoppingItem("name", "5", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}