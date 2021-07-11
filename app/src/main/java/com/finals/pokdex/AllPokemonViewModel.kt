package com.finals.pokdex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finals.pokdex.repositories.PokemonRepo
import com.finals.pokdex.response.recycler_element.ListResponse
import com.finals.pokdex.response.recycler_element.PokemonListElement
import com.finals.pokdex.utils.PAGE_SIZE
import com.finals.pokdex.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllPokemonViewModel @Inject constructor(
    private val repository:PokemonRepo
): ViewModel() {
    private val curPage=0;
    private val _pokemonList=MutableLiveData< Resources<ListResponse>>()
     val pokemonList:LiveData< Resources<ListResponse>> get() =_pokemonList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonList=repository.getPokemonList(PAGE_SIZE, PAGE_SIZE*curPage)
            _pokemonList.postValue(pokemonList)
        }

    }

}