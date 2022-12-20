package com.example.stablediffuser.ui.mosaic

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
import com.example.stablediffuser.config.Configuration.HTTP_ERROR_TOO_MANY_REQUESTS
import com.example.stablediffuser.config.Configuration.HTTP_HEADER_RETRY_AFTER
import com.example.stablediffuser.config.Configuration.searchRepository
import com.example.stablediffuser.databinding.FragmentMosaicBinding
import com.example.stablediffuser.databinding.SheetRetryLaterBinding
import com.example.stablediffuser.network.lexica.LexicaError
import com.example.stablediffuser.network.lexica.LexicaImage
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions
import com.example.stablediffuser.utils.extensions.setToolbarTitle
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
        viewBinding?.showLoading()

        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    searchRepository.searchForImages(mosaicQuery).also { result ->
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

    private fun showArt(image: LexicaImage) {
        MosaicFragmentDirections
            .actionNavigationMosaicToNavigationArt().apply {
                with(image) {
                    artUrl = src
                    thumbUrl = srcSmall
                    artTitle = prompt
                    artSize = "$width x $height"
                    artNsfw = nsfw
                }
            }.also { action ->
                findNavController().navigate(
                    action,
                    defaultScreenNavOptions
                )
            }
    }

    private fun showRetryLaterSheet(
        retryMinutes: Int
    ) {
        val sheetView = SheetRetryLaterBinding.inflate(layoutInflater).also { binding ->
            "You need to rest your thumbs! Please try again in $retryMinutes mn".also { retryText ->
                binding.sheetTitle.text = retryText
            }
        }.root

        BottomSheetDialog(requireContext()).also { dialog ->
            dialog.setOnShowListener {
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            dialog.setContentView(sheetView)
        }.show()
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

    private fun FragmentMosaicBinding.showLoading() = showState(null)

    private fun FragmentMosaicBinding.showState(result: Result<List<LexicaImage>>? = null) {
        loadingIndicator.isVisible = result == null
        errorIndicator.isVisible = result?.isFailure ?: false
        emptyIndicator.isVisible = result?.getOrNull()?.isEmpty() ?: false
        mosaicContent.isVisible = result?.getOrNull()?.isNotEmpty() ?: false
    }
}