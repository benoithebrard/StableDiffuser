package com.example.stablediffuser.ui.mosaic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.R
import com.example.stablediffuser.databinding.FragmentMosaicBinding
import com.example.stablediffuser.utils.NavigationHelper

class MosaicFragment : Fragment() {

    private var viewBinding: FragmentMosaicBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mosaicViewModel = ViewModelProvider(this)[MosaicViewModel::class.java]

        viewBinding = FragmentMosaicBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMosaic
        mosaicViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.artButton.setOnClickListener {
            findNavController().navigate(R.id.art_dest, null, NavigationHelper.options)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}