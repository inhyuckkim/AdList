package com.inhyuck.adlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.inhyuck.adlist.R
import com.inhyuck.adlist.databinding.FragmentAdlistBinding
import com.inhyuck.adlist.db.MainDB
import com.inhyuck.adlist.viewmodel.AdListVMFactory
import com.inhyuck.adlist.viewmodel.AdListViewModel

class AdListFragment : Fragment() {

    private val adListViewModel: AdListViewModel by viewModels { AdListVMFactory.setDB(MainDB.getInstance(requireContext()))}

    private lateinit var binding: FragmentAdlistBinding

    private lateinit var adapter: AdAdapter

    private fun initAdList(viewModel: AdListViewModel){
        viewModel.adList.observe(viewLifecycleOwner, Observer { listResource ->
            if (listResource != null){
                adapter.submitList(listResource)
            }else{
                adapter.submitList(emptyList())
            }
        })

        viewModel.foundError.observe(viewLifecycleOwner, Observer { msg ->
            msg?.let { it ->
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                adListViewModel.foundError.value = null
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAdlistBinding>(
            inflater,
            R.layout.fragment_adlist,
            container,
            false
        )
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = requireActivity().applicationInfo.loadLabel(requireActivity().packageManager).toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adlist = adListViewModel.adList

        val adAdapter= AdAdapter{
            val bundle = bundleOf(
                "appId" to it.appId
            )
            findNavController().navigate(
                R.id.adDetail,
                bundle
            )
            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.title = it.productName
        }

        this.adapter = adAdapter
        binding.recyclerview.adapter = adAdapter

        //TODO: Check internet connectivity
        initAdList(adListViewModel)
        super.onViewCreated(view, savedInstanceState)
    }
}