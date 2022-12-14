package com.benoithebrard.stablediffuser.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.benoithebrard.stablediffuser.databinding.FragmentFavoritesBinding
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository
import com.benoithebrard.stablediffuser.ui.art.ArtData
import com.benoithebrard.stablediffuser.ui.mosaic.MosaicViewModel
import com.benoithebrard.stablediffuser.ui.mosaic.toMosaicCellViewModels
import com.benoithebrard.stablediffuser.utils.NavOptionsHelper
import com.benoithebrard.stablediffuser.utils.extensions.setupAutoOrientationGrid
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var viewBinding: FragmentFavoritesBinding? = null

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    private val mosaicViewModel: MosaicViewModel by lazy {
        MosaicViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoritesBinding.inflate(inflater, container, false).apply {
        viewModel = mosaicViewModel
    }.also { binding ->
        viewBinding = binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.setupUI()

        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    favoritesRepository.favoritesFlow.collectLatest { favoriteArts ->
                        favoriteArts.toMosaicCellViewModels { artData ->
                            showArt(artData)
                        }.also { viewModels ->
                            mosaicViewModel.showContent(viewModels)
                        }
                        viewBinding?.showState(favoriteArts)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun showArt(artData: ArtData) {
        FavoritesFragmentDirections.actionFavoritesDestToArtDest().apply {
            artId = artData.id
            artUrl = artData.url
            thumbUrl = artData.thumbUrl
            artTitle = artData.prompt
            artDimensions = artData.dimensions
            artNsfw = artData.nsfw
        }.also { action ->
            findNavController().navigate(
                action,
                NavOptionsHelper.slidingNavOptions
            )
        }
    }

    private fun FragmentFavoritesBinding.setupUI() {
        favoritesContent.setupAutoOrientationGrid()
        addFavoriteButton.setOnClickListener {
            FavoritesFragmentDirections.actionFavoritesDestToSearchDest().also { action ->
                findNavController().navigate(
                    action,
                    NavOptionsHelper.fadingNavOptions
                )
            }
        }
        showState(null)
    }

    private fun FragmentFavoritesBinding.showState(favoriteArts: List<ArtData>? = null) {
        loadingIndicator.isVisible = favoriteArts == null
        emptyIndicator.isVisible = favoriteArts?.isEmpty() ?: false
        favoritesContent.isVisible = favoriteArts?.isNotEmpty() ?: false
    }
}