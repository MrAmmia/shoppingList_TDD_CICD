package net.thebookofcode.shoppinglistitemswithtdd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingDao
import net.thebookofcode.shoppinglistitemswithtdd.data.remote.PixabayAPI
import net.thebookofcode.shoppinglistitemswithtdd.repositories.DefaultShoppingRepository
import net.thebookofcode.shoppinglistitemswithtdd.repositories.ShoppingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

}