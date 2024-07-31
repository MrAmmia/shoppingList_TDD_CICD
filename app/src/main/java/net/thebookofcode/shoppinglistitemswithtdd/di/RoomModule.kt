package net.thebookofcode.shoppinglistitemswithtdd.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.room.Room
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItemDatabase
import net.thebookofcode.shoppinglistitemswithtdd.other.Constants.DATABASE_NAME

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideShoppingDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            ShoppingItemDatabase::class.java,
            DATABASE_NAME
        ).build()


    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()
}