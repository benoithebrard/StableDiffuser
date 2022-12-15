package com.example.stablediffuser.ui.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.R
import com.example.stablediffuser.network.repositories.QueryRepository
import com.example.stablediffuser.databinding.FragmentSearchBinding
import com.example.stablediffuser.utils.NavOptionsHelper.defaultScreenNavOptions

private const val MIN_COUNT_CHARACTERS_SEARCH = 3

class SearchFragment : Fragment() {

    private var viewBinding: FragmentSearchBinding? = null

    private val queryRepository: QueryRepository by lazy {
        QueryRepository(
            sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        )
    }

    private val searchViewModel: SearchViewModel by lazy {
        SearchViewModel(
            onShowMosaic = {
                viewBinding?.handleSearch()
            },
            onClearSearch = {
                viewBinding?.searchBox?.text?.clear()
            },
            onClearQueries = { view ->
                view.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.shake))
                queryRepository.apply {
                    clearAll()
                    save()
                }
                searchViewModel.setQueries(queryRepository.getAll())
            },
            onLoadQuery = { query ->
                viewBinding?.searchBox?.setText(query)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        queryRepository.restore()
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
        viewBinding?.setupUI()

        queryRepository.getAll().also { queries ->
            searchViewModel.setQueries(queries)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding?.searchBox?.setOnKeyListener(null)
        viewBinding = null
    }

    override fun onDestroy() {
        queryRepository.save()
        super.onDestroy()
    }

    private fun FragmentSearchBinding.setupUI() {
        searchBox.doAfterTextChanged {
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

    private fun FragmentSearchBinding.handleSearch() {
        SearchFragmentDirections
            .actionNavigationSearchToNavigationMosaic().apply {
                mosaicQuery = searchQuery
                mosaicTitle = mosaicQuery
            }.also { action ->
                findNavController().navigate(action, defaultScreenNavOptions)
            }
        queryRepository.add(searchQuery)
    }

    private val FragmentSearchBinding.searchQuery: String
        get() = searchBox.text.toString()
}