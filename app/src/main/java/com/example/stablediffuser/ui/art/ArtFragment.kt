package com.example.stablediffuser.ui.art

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stablediffuser.R
import com.example.stablediffuser.databinding.FragmentArtBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions
import com.example.stablediffuser.utils.NavOptionsHelper.popToSearchNavOptions
import com.example.stablediffuser.utils.extensions.setToolbarTitle
import com.example.stablediffuser.utils.extensions.toTitle

class ArtFragment : Fragment() {

    private val artArgs: ArtFragmentArgs by navArgs()

    private val artUrl: String by lazy { artArgs.artUrl }

    private val artTitle: String by lazy { artArgs.artTitle }

    private var viewBinding: FragmentArtBinding? = null

    private val artViewModel: ArtViewModel by lazy {
        ArtViewModel(
            title = "This is an art Fragment",
            imageUrl = artUrl,
            onShowMosaic = {
                ArtFragmentDirections
                    .actionNavigationArtToNavigationMosaic().apply {
                        mosaicQuery = artUrl
                        mosaicTitle = "More of: ${artTitle.toTitle()}"
                    }.also { action ->
                        findNavController().navigate(action, defaultScreenNavOptions)
                    }
            },
            onShowSearch = {
                findNavController().navigate(R.id.search_dest, null, popToSearchNavOptions)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentArtBinding.inflate(inflater, container, false).apply {
        viewModel = artViewModel
    }.also { binding ->
        viewBinding = binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(artTitle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}