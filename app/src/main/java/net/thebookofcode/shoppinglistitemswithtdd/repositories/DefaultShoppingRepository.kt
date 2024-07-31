package net.thebookofcode.shoppinglistitemswithtdd.repositories

import androidx.lifecycle.LiveData
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingDao
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItem
import net.thebookofcode.shoppinglistitemswithtdd.data.remote.PixabayAPI
import net.thebookofcode.shoppinglistitemswithtdd.data.remote.responses.ImageResponse
import net.thebookofcode.shoppinglistitemswithtdd.other.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
): ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.InsertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.DeleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observerTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("An unknown error occurred",null)
            }else{
                Resource.error("An unknown error occurred",null)
            }
        }catch (e:Exception){
            Resource.error("Couldn't reach the server. Check your internet connection",null)
        }
    }
}