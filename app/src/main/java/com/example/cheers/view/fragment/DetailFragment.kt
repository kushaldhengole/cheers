package com.example.cheers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cheers.R
import com.example.cheers.databinding.FragmentDetailBinding
import com.example.cheers.util.hideBottomSheet
import com.example.cheers.viewModel.BeerDetailViewModel
import com.example.cheers.viewModel.BeerListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    var binding:FragmentDetailBinding?=null
    var hideBottomSheet:hideBottomSheet?=null
    val viewModel:BeerDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding = FragmentDetailBinding.inflate(inflater,container,false)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val supportActionBar: ActionBar? = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.hide()
         viewModel.selectedBeerid = arguments?.getString("id")?.toLong()
        viewModel.getSelectedBeer()
        binding?.toolbarDetail?.setOnClickListener {
            onBackPressed()
        }

    }

    private fun onBackPressed() {
        findNavController().popBackStack()
    }

    override fun onResume() {
        super.onResume()
        hideBottomSheet?.showBottomSheet(false)
    }

    override fun onStop() {
        super.onStop()
        hideBottomSheet?.showBottomSheet(true)
    }
}