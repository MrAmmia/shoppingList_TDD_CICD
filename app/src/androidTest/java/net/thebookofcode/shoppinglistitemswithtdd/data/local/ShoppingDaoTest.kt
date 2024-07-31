package net.thebookofcode.shoppinglistitemswithtdd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.test.runTest
import net.thebookofcode.shoppinglistitemswithtdd.getOrAwaitValue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Named

@RunWith(AndroidJUnit4::class)
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()

        dao = database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val item = ShoppingItem("name", 1, 1f, "url", 1)
        dao.InsertShoppingItem(item)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).contains(item)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val item = ShoppingItem("name", 1, 1f, "url", 1)
        dao.InsertShoppingItem(item)
        dao.DeleteShoppingItem(item)

        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(item)
    }

    @Test
    fun observeTotalPriceSum() = runTest {
        val item1 = ShoppingItem("name1", 2, 10f, "url1", 1)
        val item2 = ShoppingItem("name2", 4, 5.5f, "url2", 2)
        val item3 = ShoppingItem("name3", 0, 100f, "url3", 3)

        dao.InsertShoppingItem(item1)
        dao.InsertShoppingItem(item2)
        dao.InsertShoppingItem(item3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(2*10f + 4*5.5f)
    }
}