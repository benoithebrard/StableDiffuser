package com.example.stablediffuser.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.config.Configuration
import com.example.stablediffuser.databinding.FragmentFavoritesBinding
import com.example.stablediffuser.network.repositories.FavoritesRepository
import com.example.stablediffuser.ui.art.ArtData
import com.example.stablediffuser.ui.mosaic.toMosaicCellViewModels
import com.example.stablediffuser.utils.NavOptionsHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var viewBinding: FragmentFavoritesBinding? = null

    private val favoritesRepository: FavoritesRepository by lazy {
        Configuration.favoritesRepository
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoritesBinding.inflate(inflater, container, false).also { binding ->
        viewBinding = binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    favoritesRepository.favoritesFlow.collectLatest { favoriteArts ->
                        val viewModels = favoriteArts.toMosaicCellViewModels { artData ->
                            showArt(artData)
                        }
                        // TODO: show view models in grid layout
                    }
                }
            }
        }
    }

    private fun showArt(artData: ArtData) {
        FavoritesFragmentDirections.actionFavoritesDestToArtDest().apply {
            artId = artData.id
            artUrl = artData.url
            thumbUrl = artData.thumbUrl
            artTitle = artData.prompt
            artSize = artData.dimensions
            artNsfw = artData.nsfw
        }.also { action ->
            findNavController().navigate(
                action,
                NavOptionsHelper.defaultScreenNavOptions
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}