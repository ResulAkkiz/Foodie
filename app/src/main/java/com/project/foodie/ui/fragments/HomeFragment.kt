package com.project.foodie.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.project.foodie.R
import com.project.foodie.data.entity.Yemek
import com.project.foodie.databinding.FragmentHomeBinding
import com.project.foodie.databinding.FragmentLoginBinding
import com.project.foodie.ui.adapters.YemekRecyclerViewAdapter
import com.project.foodie.ui.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var yemekListesi = listOf<Yemek>()
    lateinit var adapter: YemekRecyclerViewAdapter
    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.yemeklerRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        binding.progressBar2.visibility=View.VISIBLE
        viewModel.yemekList.observe(viewLifecycleOwner) {yemekList->
            yemekListesi=yemekList
            adapter = YemekRecyclerViewAdapter(yemekList, requireContext())
            binding.yemeklerRecyclerView.adapter = adapter
            binding.progressBar2.visibility=View.GONE
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                println(newText)
                return true
            }

        }
        )

        return view
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<Yemek>()
            for (item in yemekListesi) {
                if (item.yemekName.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(item)
                }
            }
            if (filteredList.isNotEmpty()) {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}