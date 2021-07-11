package com.finals.pokdex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import com.finals.pokdex.adapter.PokemonAdapter
import com.finals.pokdex.databinding.FragmentFirstBinding
import com.finals.pokdex.response.recycler_element.PokemonListElement
import com.finals.pokdex.utils.Resources
import com.finals.pokdex.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class AllPokemon  : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val viewModel: AllPokemonViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf<PokemonListElement>(

        )
        val adapter = PokemonAdapter()
        adapter.submitList(list)
        binding.recyclerView2.apply {
            this.adapter = adapter;
            layoutManager = GridLayoutManager(requireContext(), 2);
            setHasFixedSize(true)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pokemonList.observe(viewLifecycleOwner, {
                when (it) {
                    is Resources.Success -> {
                        adapter.submitList(it.data?.results)
                    }
                    is Resources.Error -> {
                        it.error?.let { it1 -> showSnackbar(it1) }
                    }
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}