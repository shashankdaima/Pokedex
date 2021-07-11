package com.finals.pokdex.repositories

import com.finals.pokdex.response.recycler_element.ListResponse
import com.finals.pokdex.retrofit.PokeApi
import com.finals.pokdex.utils.Resources
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class PokemonRepo @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): Resources<ListResponse> =
        try {
            Resources.Success(api.getPokemonList(limit, offset))
        } catch (e: Exception) {
            Resources.Error("Error:${e.localizedMessage}")
        }



}