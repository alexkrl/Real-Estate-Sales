package com.alexk.nadlansales.ui.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.alexk.nadlansales.model.network.esates.EstateInfo
import com.alexk.nadlansales.databinding.EstateDataItemBinding

/**
 * Created by alexkorolov on 25/03/2020.
 */
class EstateHistoryHolder(private val estateBinding: EstateDataItemBinding) :
    RecyclerView.ViewHolder(estateBinding.root) {

    fun bind(
        estateInfo: EstateInfo
    ) {
        estateBinding.estateInfo = estateInfo
    }
}