package com.example.stablediffuser.ui.dummy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.R
import com.example.stablediffuser.databinding.FragmentDummyBinding
import com.example.stablediffuser.databinding.FragmentSearchBinding

class DummyFragment : Fragment() {

    private var viewBinding: FragmentDummyBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dummyViewModel = ViewModelProvider(this)[DummyViewModel::class.java]

        viewBinding = FragmentDummyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDummy
        dummyViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.search_dest, null)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}