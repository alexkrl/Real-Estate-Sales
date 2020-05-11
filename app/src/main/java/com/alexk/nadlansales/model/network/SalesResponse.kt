package com.alexk.nadlansales.model.network

import com.alexk.nadlansales.model.network.estates.EstateInfo

data class SalesResponse(
    val AllResults: List<EstateInfo>,
    val IsLastPage: Boolean,
    val IsSpecificAddressResult: Boolean,
    val PageNumber: Int,
    val QueryMapParams: Any,
    val SpecificAddressData: List<EstateInfo>
)