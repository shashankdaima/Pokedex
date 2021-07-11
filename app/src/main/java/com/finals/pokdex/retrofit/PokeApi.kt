package com.finals.pokdex.retrofit

import com.finals.pokdex.response.recycler_element.ListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {

    @GET(value = "pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset:Int
        ):ListResponse
}
