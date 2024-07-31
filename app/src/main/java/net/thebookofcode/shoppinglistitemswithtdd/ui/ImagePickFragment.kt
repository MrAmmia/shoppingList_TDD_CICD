package net.thebookofcode.shoppinglistitemswithtdd.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import net.thebookofcode.shoppinglistitemswithtdd.R
import net.thebookofcode.shoppinglistitemswithtdd.adapters.ImageAdapter
import net.thebookofcode.shoppinglistitemswithtdd.databinding.FragmentImagePickBinding
import net.thebookofcode.shoppinglistitemswithtdd.logic.ShoppingViewModel
import net.thebookofcode.shoppinglistitemswithtdd.other.Constants.GRID_SPAN_COUNT
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment() {
    private var _binding:FragmentImagePickBinding? = null
    private val binding get() = _binding!!
    //lateinit var viewModel: ShoppingViewModel
    val viewModel:ShoppingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImagePickBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //    viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurrentImageUrl(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() = with(binding) {
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }
}