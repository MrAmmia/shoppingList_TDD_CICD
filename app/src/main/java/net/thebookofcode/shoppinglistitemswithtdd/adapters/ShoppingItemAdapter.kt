package net.thebookofcode.shoppinglistitemswithtdd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.data.local.ShoppingItem
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import net.thebookofcode.shoppinglistitemswithtdd.databinding.ItemShoppingBinding
import javax.inject.Inject


class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {

    class ShoppingItemViewHolder(
        private val itemBinding: ItemShoppingBinding,
        private val glide: RequestManager
    ) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(shoppingItem: ShoppingItem) = with(itemBinding){
            glide.load(shoppingItem.imageUrl).into(ivShoppingImage)

            tvName.text = shoppingItem.name
            val amountText = "${shoppingItem.amount}x"
            tvShoppingItemAmount.text = amountText
            val priceText = "${shoppingItem.price}€"
            tvShoppingItemPrice.text = priceText
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(
            ItemShoppingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), glide
        )
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        holder.bind(shoppingItem)
    }
}