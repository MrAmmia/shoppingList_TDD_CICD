package net.thebookofcode.shoppinglistitemswithtdd.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItemDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestRoomModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryShoppingDB(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()
}