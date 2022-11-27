package com.example.stablediffuser.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.databinding.FragmentSearchBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions

private const val MIN_COUNT_CHARACTERS_SEARCH = 3

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null

    private val searchViewModel: SearchViewModel by lazy {
        SearchViewModel(
            title = "This is a search Fragment",
            onShowMosaic = {
                viewBinding?.apply {
                    searchBox.doAfterTextChanged {

                    }
                    searchBox.doOnTextChanged { text, start, before, count -> }
                    SearchFragmentDirections
                        .actionNavigationSearchToNavigationMosaic().apply {
                            mosaicQuery = searchBox.text.toString()
                            mosaicTitle = mosaicQuery
                        }.also { action ->
                            findNavController().navigate(action, defaultScreenNavOptions)
                        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.apply {
            searchBox.doAfterTextChanged {
                val searchQuery = searchBox.text.toString()
                searchViewModel.isSearchEnabled.set(searchQuery.length > MIN_COUNT_CHARACTERS_SEARCH)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}