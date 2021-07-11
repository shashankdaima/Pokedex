package com.finals.pokdex.response.recycler_element

data class ListResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<PokemonListElement>
)