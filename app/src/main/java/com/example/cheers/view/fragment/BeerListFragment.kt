package com.example.cheers.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cheers.databinding.FragmentBeerListBinding
import com.example.cheers.model.commonModels.RepositoryResult
import com.example.cheers.util.Extendtion.safeNavigate
import com.example.cheers.view.adapters.BeerListAdapter
import com.example.cheers.viewModel.BeerListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerListFragment : Fragment() {

    private var beerAdapter: BeerListAdapter?=null
    private var _binding: FragmentBeerListBinding? = null
    val viewModel:BeerListViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerListBinding.inflate(inflater, container, false)
        Log.e("url","${viewModel.beerList.value}")
        _binding?.viewmodel = viewModel
        _binding?.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        setOserver()

    }

    private fun setOserver() {
        viewModel.beerList.observe(viewLifecycleOwner){ result->
             when(result){
                 is RepositoryResult.Success->{
                     beerAdapter?.submitList(result.data)
                 }
                 else->{

                 }
             }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBeerList()

    }

    private fun setDataToRecyclerView() {
    }

    private fun setRecyclerView() {
         beerAdapter= BeerListAdapter { beerid ->
             val action = BeerListFragmentDirections.actionNavigationListToNavigationDetail ()
             action.id = beerid.toString()
             findNavController().safeNavigate(action)
         }
        binding.rvBeer.apply {
            adapter= beerAdapter
            hasFixedSize()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}