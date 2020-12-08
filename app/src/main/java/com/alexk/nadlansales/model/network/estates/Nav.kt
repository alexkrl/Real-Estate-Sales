package com.alexk.nadlansales.model.network.estates

import java.io.Serializable

data class Nav(
    val order: Int,
    val text: String,
    val url: String
) : Serializable