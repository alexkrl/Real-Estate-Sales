package com.alexk.nadlansales.ui.search_estates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alexk.nadlansales.R
import com.alexk.nadlansales.model.entities.Street

/**
 * Created by alexkorolov on 01/04/2020.
 */
class AddressViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.address_list_item, parent, false)) {

    private var address: TextView? = null
    private var root: View? = null


    init {
        address = itemView.findViewById(R.id.adreessName)
        root = itemView.findViewById(R.id.root)
    }

    fun bind(
        addressData: Street,
        onItemClick: (Street) -> Unit
    ) {
        address?.text = addressData.Value

        root?.setOnClickListener {
            onItemClick(addressData)
        }
    }
}