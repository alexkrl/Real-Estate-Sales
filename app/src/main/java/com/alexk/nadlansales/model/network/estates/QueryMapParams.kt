package com.alexk.nadlansales.model.network.estates

import java.io.Serializable

data class QueryMapParams(
    val QueryDescLayerID: String,
    val QueryObjectID: String,
    val QueryObjectKey: String,
    val QueryObjectType: String,
    val QueryToRun: Any,
    val SpacialWhereClause: Any
) : Serializable