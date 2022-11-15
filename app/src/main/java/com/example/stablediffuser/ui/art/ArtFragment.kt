package com.example.stablediffuser.ui.art

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
import com.example.stablediffuser.databinding.FragmentArtBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions
import com.example.stablediffuser.utils.NavOptionsHelper.popToSearchNavOptions

class ArtFragment : Fragment() {

    private val artArgs: ArtFragmentArgs by navArgs()

    private val artUrl: String by lazy { artArgs.artUrl }

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

        val textView: TextView = binding.textArt
        artViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.mosaicButton.setOnClickListener {
            ArtFragmentDirections
                .actionNavigationArtToNavigationMosaic()
                .setMosaicUrl("http://some.cool.mosaic.url.from.art")
                .also { action ->
                    findNavController().navigate(action, defaultScreenNavOptions)
                }
        }

        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.search_dest, null, popToSearchNavOptions)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tot = artUrl
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}