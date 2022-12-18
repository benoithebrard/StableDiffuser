package com.example.stablediffuser.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stablediffuser.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var viewBinding: FragmentFavoritesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoritesBinding.inflate(inflater, container, false).also { binding ->
        viewBinding = binding
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FavoritesFragmentDirections
            .actionNavigationFavoritesToNavigationMosaic().also { action ->
                findNavController().navigate(action)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}