package com.example.stablediffuser.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.databinding.FragmentSearchBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null

    private val searchViewModel: SearchViewModel by lazy {
        SearchViewModel(
            title = "This is a search Fragment",
            onMosaicClicked = {
                SearchFragmentDirections
                    .actionNavigationSearchToNavigationMosaic()
                    .setMosaicUrl("http://some.cool.mosaic.url")
                    .also { action ->
                        findNavController().navigate(action, defaultScreenNavOptions)
                    }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).apply {
        viewModel = searchViewModel
    }.also { binding ->
        viewBinding = binding
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}