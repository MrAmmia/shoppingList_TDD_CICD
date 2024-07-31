package net.thebookofcode.shoppinglistitemswithtdd.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import net.thebookofcode.shoppinglistitemswithtdd.adapters.ImageAdapter
import net.thebookofcode.shoppinglistitemswithtdd.adapters.ShoppingItemAdapter
import net.thebookofcode.shoppinglistitemswithtdd.logic.ShoppingViewModel
import net.thebookofcode.shoppinglistitemswithtdd.repositories.FakeShoppingRepositoryAndroidTest
import javax.inject.Inject

class TestShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingItemAdapter
                //ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
            )

            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}