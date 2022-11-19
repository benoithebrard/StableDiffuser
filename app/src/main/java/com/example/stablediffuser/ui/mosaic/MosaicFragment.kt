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
import com.example.stablediffuser.config.Configuration.lexicaRepository
import com.example.stablediffuser.data.image.LexicaImage
import com.example.stablediffuser.databinding.FragmentMosaicBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions
import com.example.stablediffuser.utils.extensions.setToolbarTitle
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
        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    val result = lexicaRepository.searchForImages(mosaicQuery)
                    result.getOrNull()?.let { images ->
                        val cellViewModels = images.toMosaicCellViewModels(
                            onShowArt = ::showArt
                        )
                        mosaicViewModel.showContent(cellViewModels)
                    }
                    viewBinding?.showState(result)
                }
            }
        }
    }

    private fun showArt(imageUrl: String, prompt: String) {
        MosaicFragmentDirections
            .actionNavigationMosaicToNavigationArt().apply {
                artUrl = imageUrl
                artTitle = prompt
            }.also { action ->
                findNavController().navigate(
                    action,
                    defaultScreenNavOptions
                )
            }
    }

    private fun FragmentMosaicBinding.showState(result: Result<List<LexicaImage>>? = null) {
        loadingIndicator.isVisible = result == null
        errorIndicator.isVisible = result?.isFailure ?: false
        emptyIndicator.isVisible = result?.getOrNull()?.isEmpty() ?: false
        mosaicContent.isVisible = result?.getOrNull()?.isNotEmpty() ?: false
    }
}