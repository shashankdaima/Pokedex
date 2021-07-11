package com.finals.pokdex.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.finals.pokdex.R
import com.finals.pokdex.databinding.PokemonListElementBinding
import com.finals.pokdex.response.recycler_element.PokemonListElement
import com.finals.pokdex.utils.calcDominantColor
import com.finals.pokdex.utils.drawable_from_url
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@ActivityScoped
class PokemonAdapter : ListAdapter<PokemonListElement, PokemonAdapter.ViewHolder>(Comparator()) {
    inner class ViewHolder(val binding: PokemonListElementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonListElement: PokemonListElement) {
            binding.name.setText(pokemonListElement.name)
            val number = if (pokemonListElement.url.endsWith("/")) {
                pokemonListElement.url.dropLast(1).takeLastWhile { it.isDigit() }
            } else {
                pokemonListElement.url.takeLastWhile { it.isDigit() }
            }
            val uri = Uri.parse(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
            )
            Glide.with(itemView)
                .load(uri)
                .error(R.drawable.ic_error)
                .into(binding.image)
            CoroutineScope(Dispatchers.Default).launch {
                if(pokemonListElement.color==null){
                    drawable_from_url(uri.toString())?.let {
                        calcDominantColor(it) {color->
                            pokemonListElement.color=color
                            CoroutineScope(Dispatchers.Main).launch {
                                val gd = GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM,
                                    intArrayOf(color, Color.WHITE)
                                )
                                gd.cornerRadius = 0f
                                binding.card.background = gd
                            }
                        }
                    }
                }
                else{
                    CoroutineScope(Dispatchers.Main).launch {
                        val gd = GradientDrawable(
                            GradientDrawable.Orientation.TOP_BOTTOM,
                            intArrayOf(pokemonListElement.color!!, Color.WHITE)
                        )
                        gd.cornerRadius = 0f
                        binding.card.background = gd
                    }
                }
            }

        }
    }

    class Comparator : DiffUtil.ItemCallback<PokemonListElement>() {
        override fun areItemsTheSame(oldItem: PokemonListElement, newItem: PokemonListElement) =
            oldItem.name == newItem.name


        override fun areContentsTheSame(oldItem: PokemonListElement, newItem: PokemonListElement) =
            newItem == oldItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            PokemonListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }
}