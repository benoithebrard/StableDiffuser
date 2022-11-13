package com.example.stablediffuser.ui.mosaic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stablediffuser.R
import com.example.stablediffuser.databinding.FragmentMosaicBinding
import com.example.stablediffuser.utils.NavOptionsHelper
import com.example.stablediffuser.utils.NavOptionsHelper.popSearchNavOptions

class MosaicFragment : Fragment() {

    private val safeArgs: MosaicFragmentArgs by navArgs()

    private val mosaicUrl: String by lazy { safeArgs.mosaicUrl }

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
            findNavController().navigate(R.id.art_dest, null, NavOptionsHelper.showScreenNavOptions)
        }

        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.search_dest, null, popSearchNavOptions)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tot = mosaicUrl
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}