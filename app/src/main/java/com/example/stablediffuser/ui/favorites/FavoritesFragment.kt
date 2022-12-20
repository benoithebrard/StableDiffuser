package com.example.stablediffuser.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.stablediffuser.config.Configuration
import com.example.stablediffuser.databinding.FragmentFavoritesBinding
import com.example.stablediffuser.network.repositories.FavoritesRepository

class FavoritesFragment : Fragment() {

    private var viewBinding: FragmentFavoritesBinding? = null

    private val favoritesRepository: FavoritesRepository by lazy {
        Configuration.favoritesRepository
    }

    override fun onStart() {
        super.onStart()
        favoritesRepository.restore()
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
        viewBinding?.apply {
            emptyText.text = "Coming soon.."
        }
    }

    override fun onStop() {
        favoritesRepository.save()
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}