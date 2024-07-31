package net.thebookofcode.shoppinglistitemswithtdd.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.databinding.FragmentAddShoppingItemBinding
import net.thebookofcode.shoppinglistitemswithtdd.logic.ShoppingViewModel
import net.thebookofcode.shoppinglistitemswithtdd.other.Status
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(
    val glide: RequestManager
) : Fragment() {
    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding!!

    //lateinit var viewModel: ShoppingViewModel
    val viewModel: ShoppingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()

        with(binding) {
            btnAddShoppingItem.setOnClickListener {
                viewModel.insertShoppingItem(
                    etShoppingItemName.text.toString(),
                    etShoppingItemAmount.text.toString(),
                    etShoppingItemPrice.text.toString()
                )
            }

            ivShoppingImage.setOnClickListener {
                navigateToImagePickScreen()
            }
        }


        pressBack()
    }

    fun pressBack() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurrentImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun navigateToImagePickScreen() {
        findNavController().navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    private fun subscribeToObservers() {
        viewModel.currentImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(binding.ivShoppingImage)
        })
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            binding.root,
                            "Added Shopping Item",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }

                    Status.ERROR -> {
                        Snackbar.make(
                            binding.root,
                            result.message ?: "An unknown error occcured",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                    Status.LOADING -> {
                        /* NO-OP */
                    }
                }
            }
        })
    }
}