package com.inhyuck.adlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.inhyuck.adlist.R
import com.inhyuck.adlist.databinding.FragmentAdBinding
import com.inhyuck.adlist.db.MainDB

class AdFragment : Fragment(){

    private lateinit var binding: FragmentAdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentAdBinding>(
            inflater,
            R.layout.fragment_ad,
            container,
            false
        )

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        val adID = arguments?.getString("appId")
        adID?.let {
            binding.adItem = MainDB.getInstance(requireContext()).AdDao().getAd(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }

}