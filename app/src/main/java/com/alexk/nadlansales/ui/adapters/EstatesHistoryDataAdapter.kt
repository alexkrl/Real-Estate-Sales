package com.alexk.nadlansales.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.alexk.nadlansales.R
import com.alexk.nadlansales.model.network.esates.EstateInfo
import com.alexk.nadlansales.databinding.EstateDataItemBinding
//import com.alexk.nadlansales.databinding.EstateDataItemBinding
import com.alexk.nadlansales.ui.viewholders.EstateHistoryHolder

/**
 * Created by alexkorolov on 08/03/2020.
 */

class EstatesHistoryDataAdapter : PagedListAdapter<EstateInfo, EstateHistoryHolder>(DIFF_CALLBACK) {

//    private val estatesInfo: MutableList<EstateInfo> = mutableListOf()
//
//    override fun getItemCount() = estatesInfo.size

    override fun onBindViewHolder(holder: EstateHistoryHolder, position: Int) = getItem(position).let {
        if (it != null) {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstateHistoryHolder {
        val inflater = LayoutInflater.from(parent.context)
        val estateDataItemBinding: EstateDataItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.estate_data_item, parent, false)
        return EstateHistoryHolder(estateDataItemBinding)
    }

//    fun setEstatesInfo(newData: List<EstateInfo>){
//        clearData()
//        estatesInfo.addAll(newData)
//        notifyDataSetChanged()
//    }
//
//    fun clearData() {
//        estatesInfo.clear()
//    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EstateInfo>() {
            override fun areItemsTheSame(oldItem: EstateInfo, newItem: EstateInfo): Boolean = true
//                oldItem.KEYVALUE == newItem.KEYVALUE

            override fun areContentsTheSame(oldItem: EstateInfo, newItem: EstateInfo): Boolean = true
//                oldItem == newItem

        }
    }
}