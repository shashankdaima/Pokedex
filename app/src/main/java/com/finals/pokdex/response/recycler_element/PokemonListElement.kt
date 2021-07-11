package com.finals.pokdex.response.recycler_element

data class PokemonListElement(
    val name: String,
    val url: String,
    var color:Int?=null
)