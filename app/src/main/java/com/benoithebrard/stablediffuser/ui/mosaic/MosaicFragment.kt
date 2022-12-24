package com.benoithebrard.stablediffuser.ui.mosaic

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
import androidx.navigation.fragment.navArgs
import com.benoithebrard.stablediffuser.R
import com.benoithebrard.stablediffuser.config.Configuration
import com.benoithebrard.stablediffuser.config.Configuration.HTTP_ERROR_TOO_MANY_REQUESTS
import com.benoithebrard.stablediffuser.config.Configuration.HTTP_HEADER_RETRY_AFTER
import com.benoithebrard.stablediffuser.databinding.FragmentMosaicBinding
import com.benoithebrard.stablediffuser.databinding.SheetRetryLaterBinding
import com.benoithebrard.stablediffuser.network.lexica.LexicaError
import com.benoithebrard.stablediffuser.ui.art.ArtData
import com.benoithebrard.stablediffuser.utils.NavOptionsHelper.slidingNavOptions
import com.benoithebrard.stablediffuser.utils.extensions.setToolbarTitle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch


class MosaicFragment : Fragment() {

    private val mosaicArgs: MosaicFragmentArgs by navArgs()

    private val mosaicQuery: String by lazy { mosaicArgs.mosaicQuery }

    private val mosaicTitle: String by lazy { mosaicArgs.mosaicTitle }

    private var viewBinding: FragmentMosaicBinding? = null

    private val mosaicViewModel: MosaicViewModel by lazy {
        MosaicViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMosaicBinding.inflate(inflater, container, false).apply {
        viewModel = mosaicViewModel
    }.also { binding ->
        viewBinding = binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(mosaicTitle)

        viewBinding?.apply {
            errorIndicator.setOnClickListener {
                searchForImages()
            }
            showState()
        }

        searchForImages()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    private fun searchForImages() {
        viewBinding?.setupUI()

        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Configuration.searchRepository.searchForQuery(mosaicQuery).also { result ->
                        result.fold(
                            onSuccess = { images ->
                                images.toMosaicCellViewModels(
                                    onShowArt = ::showArt
                                ).also { viewModels ->
                                    mosaicViewModel.showContent(viewModels)
                                }
                            },
                            onFailure = { exception ->
                                if (exception is LexicaError.Response) {
                                    exception.handleError()
                                }
                            }
                        )
                        viewBinding?.showState(result)
                    }
                }
            }
        }
    }

    private fun showArt(artData: ArtData) {
        MosaicFragmentDirections.actionNavigationMosaicToNavigationArt().apply {
            artId = artData.id
            artUrl = artData.url
            thumbUrl = artData.thumbUrl
            artTitle = artData.prompt
            artSize = artData.dimensions
            artNsfw = artData.nsfw
        }.also { action ->
            findNavController().navigate(action, slidingNavOptions)
        }
    }

    private fun showRetryLaterSheet(retryMinutes: Int) {
        val sheetView = SheetRetryLaterBinding.inflate(layoutInflater).also { binding ->
            binding.sheetTitle.text = getString(R.string.retry_later, retryMinutes)
        }.root

        BottomSheetDialog(requireContext()).also { dialog ->
            dialog.setOnShowListener {
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            dialog.setContentView(sheetView)
        }.show()
    }

    private fun FragmentMosaicBinding.setupUI() {
        showState(null)
    }

    private fun FragmentMosaicBinding.showState(result: Result<List<ArtData>>? = null) {
        loadingIndicator.isVisible = result == null
        errorIndicator.isVisible = result?.isFailure ?: false
        emptyIndicator.isVisible = result?.getOrNull()?.isEmpty() ?: false
        mosaicContent.isVisible = result?.getOrNull()?.isNotEmpty() ?: false
    }

    private fun LexicaError.Response.handleError() {
        val code = statusCode
        val headers = headers

        if (code == HTTP_ERROR_TOO_MANY_REQUESTS) {
            headers[HTTP_HEADER_RETRY_AFTER]?.let { retrySeconds ->
                retrySeconds.toInt() / 60 + 1
            }?.also { retryMinutes ->
                showRetryLaterSheet(retryMinutes)
            }
        }
    }
}