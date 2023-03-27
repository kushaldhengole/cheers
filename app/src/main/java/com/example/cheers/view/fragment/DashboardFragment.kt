package com.example.cheers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cheers.R
import com.example.cheers.databinding.FragmentDashboardBinding
import com.example.cheers.view.adapters.FoodPairedBeerAdpater
import com.example.cheers.viewModel.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    lateinit var foodPairedBeerAdpater: FoodPairedBeerAdpater
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    val viewModel:DashBoardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        _binding?.viewmodel = viewModel
        _binding?.lifecycleOwner = viewLifecycleOwner
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setRecyclerView()
        getRandBeer()
        getFoodPairedBeer()
    }

    private fun setObserver() {
        viewModel.foodPairedBeerList.observe(viewLifecycleOwner){beerList ->
            if (beerList.isNotEmpty()){
                foodPairedBeerAdpater.submitList(beerList)
            }
        }
    }


    private fun setRecyclerView() {
        foodPairedBeerAdpater = FoodPairedBeerAdpater({beerid->
            val bundle = Bundle()
            bundle.putString("id" , beerid.toString())
            findNavController().navigate(R.id.action_navigation_dashboard_to_navigation_detail,bundle)
        })
        binding.recyclerView.apply {
            adapter = foodPairedBeerAdpater
            hasFixedSize()
        }
    }

    private fun getRandBeer() {
        viewModel.getRandomBeeer()
    }

    private fun getFoodPairedBeer() {
        viewModel.getFoodpairedBeerList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}