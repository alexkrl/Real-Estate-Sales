package com.alexk.nadlansales.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.ui.viewholders.AddressViewHolder
import java.math.RoundingMode
import java.text.DecimalFormat

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

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val address: Street = dataList[position]
        holder.bind(address, onItemClick)
    }


    fun test() {
        for (i in 1..30) {
            val randomInteger = (500000..12000000).shuffled().first()

            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING

            println(df.format(randomInteger))
        }
    }
}