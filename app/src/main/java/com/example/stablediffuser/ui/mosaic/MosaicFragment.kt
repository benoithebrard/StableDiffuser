package com.example.stablediffuser.ui.mosaic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stablediffuser.R
import com.example.stablediffuser.config.Configuration
import com.example.stablediffuser.databinding.FragmentMosaicBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions
import com.example.stablediffuser.utils.NavOptionsHelper.popToSearchNavOptions
import kotlinx.coroutines.launch

class MosaicFragment : Fragment() {

    private val mosaicArgs: MosaicFragmentArgs by navArgs()

    private val mosaicUrl: String by lazy { mosaicArgs.mosaicUrl }

    private var viewBinding: FragmentMosaicBinding? = null

    private val mosaicViewModel: MosaicViewModel by lazy {
        MosaicViewModel(
            title = "This is a mosaic Fragment",
            onShowArt = {
                MosaicFragmentDirections
                    .actionNavigationMosaicToNavigationArt()
                    .setArtUrl("http://some.cool.art.url")
                    .also { action ->
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
    ): View = FragmentMosaicBinding.inflate(inflater, container, false).apply {
        viewModel = mosaicViewModel
    }.also { binding ->
        viewBinding = binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    Configuration.lexicaRepository.searchForImages("apples").fold(
                        onSuccess = { images ->
                            images.map {
                                it.srcSmall
                            }.let { imageUrls ->
                                mosaicViewModel.setImageUrls(imageUrls)
                            }
                        },
                        onFailure = { exception ->
                            val tot = exception
                        }
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}