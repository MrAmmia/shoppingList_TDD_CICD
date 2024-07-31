package net.thebookofcode.shoppinglistitemswithtdd.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.adapters.ShoppingItemAdapter
import net.thebookofcode.shoppinglistitemswithtdd.databinding.FragmentShoppingBinding
import net.thebookofcode.shoppinglistitemswithtdd.logic.ShoppingViewModel
import javax.inject.Inject
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT


@AndroidEntryPoint
class ShoppingFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter
    //var viewModel: ShoppingViewModel?=null
) : Fragment() {
    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    val viewModel:ShoppingViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel = viewModel ?: ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        subscribeToObservers()
        setupRecyclerView()

        with(binding) {
            fabAddShoppingItem.setOnClickListener {
                navigateToNextScreen()
            }
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            viewModel.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel.insertShoppingItemIntoDb(item)
                }
                show()
            }
        }
    }

    fun navigateToNextScreen() {
        findNavController().navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }

    private fun subscribeToObservers() {
        viewModel.shoppingItems.observe(viewLifecycleOwner) {
            shoppingItemAdapter.shoppingItems = it
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            val price = it ?: 0f
            val priceText = "Total Price: $price€"
            binding.tvShoppingItemPrice.text = priceText
        }
    }

    private fun setupRecyclerView() {
        binding.rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}