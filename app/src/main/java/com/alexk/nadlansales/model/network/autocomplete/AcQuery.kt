package com.alexk.nadlansales.model.network.autocomplete

data class AcQuery(
    val ContaintsWhiteSpace: Boolean,
    val DigitsCount: Int,
    val HebrewCount: Int,
    val InvalidChars: Int,
    val NEIGHBORHOODQuery: Any,
    val Nums: Any,
    val OriginalQuery: String,
    val Query: String,
    val QueryWords: List<String>,
    val SETTLEMENTQuery: String,
    val STREETQuery: String
)