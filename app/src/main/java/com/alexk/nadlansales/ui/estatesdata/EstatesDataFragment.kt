package com.alexk.nadlansales.ui.estatesdata

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alexk.nadlansales.R
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.ui.BaseFragment
import com.alexk.nadlansales.utils.hide
import com.alexk.nadlansales.utils.show
import kotlinx.android.synthetic.main.estates_data_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class EstatesDataFragment : BaseFragment(R.layout.estates_data_fragment) {

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
        viewModel.estatesPageData.observe(viewLifecycleOwner, Observer {
            estatesHistoryDataAdapter?.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            when (it){
                is State.Loading -> loading.show()
                is State.LoadingFinish -> loading.hide()
                is State.Success -> TODO()
                is State.Error -> TODO()
            }
        })
    }
}

