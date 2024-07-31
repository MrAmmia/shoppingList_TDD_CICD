package net.thebookofcode.shoppinglistitemswithtdd.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import net.thebookofcode.shoppinglistitemswithtdd.repositories.FakeShoppingRepositoryAndroidTest
import net.thebookofcode.shoppinglistitemswithtdd.repositories.ShoppingRepository

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object TestRepositoryModule {

    @Provides
    fun provideFakeRepository() = FakeShoppingRepositoryAndroidTest() as ShoppingRepository
}