package com.alexk.nadlansales.model.network

import com.alexk.nadlansales.model.network.autocomplete.Info
import com.alexk.nadlansales.model.network.autocomplete.Res

data class AutoCompleteResponse(
    val Error: Int,
    val info: Info,
    val order: List<String>,
    val res: Res
)