package com.inhyuck.adlist.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.inhyuck.adlist.R
import com.inhyuck.adlist.databinding.FragmentAdBinding
import com.inhyuck.adlist.db.MainDB
import java.net.URL
import java.util.concurrent.Executors

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
            binding.adItem = MainDB.getInstance(requireContext()).adDao().getAd(it)
            binding.adItem?.observe(viewLifecycleOwner, Observer { ad->
                callImpression(ad.impressionTrackingURL)
            })
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun callImpression(strUrl:String?){
        strUrl?.let {
            Executors.newSingleThreadExecutor().execute {
                URL(it).readText()
            }
        }
    }
}