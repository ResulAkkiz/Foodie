package com.project.foodie.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.foodie.R
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.CartAlertDialogBinding
import com.project.foodie.databinding.FragmentCartBinding
import com.project.foodie.databinding.FragmentFavoriteBinding
import com.project.foodie.databinding.FragmentHomeBinding
import com.project.foodie.firebase.FirebaseFirestoreResult
import com.project.foodie.ui.adapters.FavoriteRecyclerViewAdapter
import com.project.foodie.ui.viewmodels.FavoriteFragmentViewModel
import com.project.foodie.ui.viewmodels.ProfileFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoriteFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriteFragmentViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorites()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val view = binding.root
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.progressBar.visibility=View.VISIBLE
        viewModel.resultGetFavorites.observe(viewLifecycleOwner) { result ->
            when (result) {
                is FirebaseFirestoreResult.Success<*> -> {
                    val yemekList = result.data
                    if (yemekList is List<*>) {
                        val filteredList = yemekList.filterIsInstance<Yemek>()
                        binding.favoriteRecyclerView.adapter =
                            FavoriteRecyclerViewAdapter(filteredList, requireContext(),viewModel)
                    }
                    binding.progressBar.visibility = View.GONE
                }

                is FirebaseFirestoreResult.Failure -> {
                    Log.e("Hata:", result.error)

                    binding.progressBar.visibility = View.GONE
                }
            }


        }
//        binding.progressBar.visibility=View.GONE
        return view
    }
}