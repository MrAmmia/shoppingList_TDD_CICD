package net.thebookofcode.shoppinglistitemswithtdd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.thebookofcode.shoppinglistitemswithtdd.R
import com.bumptech.glide.RequestManager
import net.thebookofcode.shoppinglistitemswithtdd.databinding.ItemImageBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(
        private val itemBinding: ItemImageBinding,
        private val glide: RequestManager
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(url: String) = with(itemBinding) {
            glide.load(url).into(ivShoppingImage)
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var images: List<String>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            glide
        )
    }

    private var onItemClickListener: ((String) -> Unit)? = null

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = images[position]
        holder.bind(url)
        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(url)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}