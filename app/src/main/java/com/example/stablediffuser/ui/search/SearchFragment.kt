package com.example.stablediffuser.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.R
import com.example.stablediffuser.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSearch
        searchViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.mosaicButton.setOnClickListener {
            findNavController().navigate(R.id.mosaic_dest, null)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}