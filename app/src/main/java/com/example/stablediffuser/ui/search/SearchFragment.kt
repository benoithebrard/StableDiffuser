package com.example.stablediffuser.ui.search

import android.os.Bundle
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.databinding.FragmentSearchBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions

private const val MIN_COUNT_CHARACTERS_SEARCH = 3

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null

    private val searchViewModel: SearchViewModel by lazy {
        SearchViewModel(
            onShowMosaic = {
                viewBinding?.handleSearch()
            },
            onClearSearch = {
                viewBinding?.clearSearch()
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
                searchViewModel.isClearVisible.set(searchQuery.isNotEmpty())
            }

            searchBox.setOnKeyListener { view, keyCode, keyEvent ->
                when {
                    keyEvent.action == ACTION_DOWN && keyCode == KEYCODE_ENTER -> {
                        if (searchViewModel.isSearchEnabled.get()) {
                            viewBinding?.handleSearch()
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding?.apply {
            searchBox.setOnKeyListener(null)
        }
        viewBinding = null
    }

    private fun FragmentSearchBinding.handleSearch() {
        SearchFragmentDirections
            .actionNavigationSearchToNavigationMosaic().apply {
                mosaicQuery = searchBox.text.toString()
                mosaicTitle = mosaicQuery
            }.also { action ->
                findNavController().navigate(action, defaultScreenNavOptions)
            }
    }

    private fun FragmentSearchBinding.clearSearch() {
        searchBox.text?.clear()
    }
}