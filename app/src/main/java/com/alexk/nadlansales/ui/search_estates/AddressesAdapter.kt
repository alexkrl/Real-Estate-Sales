package com.alexk.nadlansales.ui.search_estates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexk.nadlansales.model.entities.Street

/**
 * Created by alexkorolov on 08/03/2020.
 */
class AddressesAdapter(
    private val dataList: List<Street>,
    private val onItemClick: (Street) -> Unit
) : RecyclerView.Adapter<AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AddressViewHolder(
            inflater,
            parent
        )
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) =
        holder.bind(dataList[position], onItemClick)
}