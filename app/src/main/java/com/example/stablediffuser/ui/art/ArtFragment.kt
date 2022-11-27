package com.example.stablediffuser.ui.art

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stablediffuser.databinding.FragmentArtBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions
import com.example.stablediffuser.utils.extensions.setToolbarTitle
import com.example.stablediffuser.utils.extensions.toTitle


class ArtFragment : Fragment() {

    private val artArgs: ArtFragmentArgs by navArgs()

    private val artUrl: String by lazy { artArgs.artUrl }

    private val artTitle: String by lazy { artArgs.artTitle }

    private val artSize: String by lazy { artArgs.artSize }

    private val artNsfw: Boolean by lazy { artArgs.artNsfw }

    private var viewBinding: FragmentArtBinding? = null

    private val clipboard: ClipboardManager by lazy {
        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    private val artViewModel: ArtViewModel by lazy {
        ArtViewModel(
            imageUrl = artUrl,
            prompt = artTitle.toTitle(),
            dimensions = artSize,
            nsfw = artNsfw,
            clipboard = clipboard,
            scope = viewLifecycleOwner.lifecycleScope,
            onShowMosaic = {
                ArtFragmentDirections
                    .actionNavigationArtToNavigationMosaic().apply {
                        mosaicQuery = artUrl
                        mosaicTitle = "More of: ${artTitle.toTitle()}"
                    }.also { action ->
                        findNavController().navigate(action, defaultScreenNavOptions)
                    }
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