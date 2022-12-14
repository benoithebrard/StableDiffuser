package com.benoithebrard.stablediffuser.ui.art

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
import com.benoithebrard.stablediffuser.R
import com.benoithebrard.stablediffuser.databinding.FragmentArtBinding
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository
import com.benoithebrard.stablediffuser.utils.NavOptionsHelper.slidingNavOptions
import com.benoithebrard.stablediffuser.utils.extensions.containsArt
import com.benoithebrard.stablediffuser.utils.extensions.setToolbarTitle
import com.benoithebrard.stablediffuser.utils.extensions.toTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val DELAY_MS_SHOW_PROMPT = 1000L

@AndroidEntryPoint
class ArtFragment : Fragment() {

    private val artArgs: ArtFragmentArgs by navArgs()

    private val artId: String by lazy { artArgs.artId }

    private val artUrl: String by lazy { artArgs.artUrl }

    private val thumbUrl: String by lazy { artArgs.thumbUrl }

    private val artTitle: String by lazy { artArgs.artTitle }

    private val artDimensions: String by lazy { artArgs.artDimensions }

    private val artNsfw: Boolean by lazy { artArgs.artNsfw }

    private val artHd: Boolean by lazy { artArgs.artHd }

    private var viewBinding: FragmentArtBinding? = null

    private val clipboard: ClipboardManager by lazy {
        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    private val artData: ArtData by lazy {
        ArtData(
            id = artId,
            url = artUrl,
            thumbUrl = thumbUrl,
            prompt = artTitle.toTitle(),
            dimensions = artDimensions,
            nsfw = artNsfw,
            hd = artHd
        )
    }

    private val artViewModel: ArtViewModel by lazy {
        ArtViewModel(
            artData = artData,
            clipboard = clipboard,
            onShowMosaic = {
                ArtFragmentDirections
                    .actionNavigationArtToNavigationMosaic().apply {
                        mosaicQuery = artUrl
                        mosaicTitle = getString(R.string.more_of, artTitle.toTitle())
                    }.also { action ->
                        findNavController().navigate(action, slidingNavOptions)
                    }
            },
            favoritesRepository = favoritesRepository
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

        with(viewLifecycleOwner) {
            lifecycleScope.launchWhenCreated {
                delay(DELAY_MS_SHOW_PROMPT)
                artViewModel.showPrompt.set(true)
            }
            lifecycleScope.launchWhenResumed {
                favoritesRepository.favoritesFlow.collectLatest { favoriteArts ->
                    artViewModel.showAsFavorite.set(favoriteArts.containsArt(artData))
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}