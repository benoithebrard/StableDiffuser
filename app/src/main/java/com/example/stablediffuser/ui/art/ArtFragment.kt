package com.example.stablediffuser.ui.art

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stablediffuser.databinding.FragmentArtBinding

class ArtFragment : Fragment() {

    private var viewBinding: FragmentArtBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val artViewModel = ViewModelProvider(this)[ArtViewModel::class.java]

        viewBinding = FragmentArtBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        artViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}