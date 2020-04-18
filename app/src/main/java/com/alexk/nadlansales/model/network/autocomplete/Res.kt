package com.alexk.nadlansales.model.network.autocomplete

import com.alexk.nadlansales.model.entities.Street
import com.google.gson.annotations.SerializedName

data class Res(
    @SerializedName("STREET", alternate = ["ADDRESS"])
    val streets: List<Street>,
    @SerializedName("SETTLEMENT")
    val settlement: List<Street>,
    @SerializedName("KPARCEL_ALL")
    val gushim: List<Street>
)