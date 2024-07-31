package net.thebookofcode.shoppinglistitemswithtdd.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItem
import net.thebookofcode.shoppinglistitemswithtdd.data.remote.responses.ImageResponse
import net.thebookofcode.shoppinglistitemswithtdd.other.Resource

class FakeShoppingRepositoryAndroidTest:ShoppingRepository {
    
    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false
    
    fun setShouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError=value
    }

    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice():Float{
        return shoppingItems.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observerTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error",null)
        }else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}