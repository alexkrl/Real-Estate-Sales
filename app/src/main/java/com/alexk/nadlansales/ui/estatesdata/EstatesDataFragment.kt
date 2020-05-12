package com.alexk.nadlansales.ui.estatesdata

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.alexk.nadlansales.R
import com.alexk.nadlansales.ui.BaseFragment
import kotlinx.android.synthetic.main.sales_data_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class EstatesDataFragment : BaseFragment(R.layout.sales_data_fragment) {

    private val viewModel: EstatesDataViewModel by viewModel()
    private var estatesHistoryDataAdapter: EstatesHistoryDataAdapter? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        esteteshistoryRecycler.apply {
            estatesHistoryDataAdapter = EstatesHistoryDataAdapter()
            adapter = estatesHistoryDataAdapter

        }

        arguments?.let { bundle ->
            val safeArgs = EstatesDataFragmentArgs.fromBundle(bundle)
            val queryStreet = safeArgs.queryStreet
            queryStreet?.Value?.let { initViewModelListener(it) }
        }


    }

    private fun initViewModelListener(address: String) {
        viewModel.queryAddress = address
        viewModel.estatesDataList.observe(viewLifecycleOwner, Observer {
            estatesHistoryDataAdapter?.submitList(it)
        })
    }
}

