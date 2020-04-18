package com.alexk.nadlansales.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.alexk.nadlansales.R
import com.alexk.nadlansales.data.EstateItemsRepository
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.model.network.esates.EstateQueryJson
import com.alexk.nadlansales.ui.adapters.EstatesHistoryDataAdapter
import com.alexk.nadlansales.ui.viewmodel.SalesDataViewModel
import com.alexk.nadlansales.utils.show
import kotlinx.android.synthetic.main.sales_data_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class SalesDataFragment : Fragment(R.layout.sales_data_fragment) {

    private val viewModel: SalesDataViewModel by viewModel()
    private var estatesHistoryDataAdapter: EstatesHistoryDataAdapter? = null
    private val estateApi : EstateApi by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        esteteshistoryRecycler.apply {
            estatesHistoryDataAdapter = EstatesHistoryDataAdapter()
            adapter = estatesHistoryDataAdapter
        }

        val json: EstateQueryJson = arguments?.getSerializable("queryJson") as EstateQueryJson
        viewModel.estateItemsRepository = EstateItemsRepository(estateApi , json)

        viewModel.getPosts().observe(viewLifecycleOwner, Observer {

            println("ALEX_TAG - ${it.size}")
            println("ALEX_TAG - SalesDataFragment->onActivityCreated------------- \n ${it.size}")
            estatesHistoryDataAdapter?.submitList(it)
        })

//        viewModel.getEstatesData(json)
        println("ALEX_TAG - SalesDataFragment->onActivityCreated")
//        viewModel.getEstatesData(json)
    }

    override fun onStart() {
        super.onStart()


        loading.show()
        println("ALEX_TAG - SalesDataFragment->onStart request data")
//        viewModel.estatesData.observe(viewLifecycleOwner, this)
//        viewModel.getPosts().observe(viewLifecycleOwner, Observer {
//            estatesHistoryDataAdapter?.submitList(it)
//        })

    }

//    private fun loadDataToRecycler(dataList: List<EstateInfo>) {
//        estatesHistoryDataAdapter?.setEstatesInfo(dataList)
//
//    }

//    override fun onChanged(dataList: List<EstateInfo>?) {
//        println("ALEX_TAG - SalesDataFragment->onChanged got data ${dataList?.size}")
//        loading.hide()
//        dataList?.let { loadDataToRecycler(it) }
//    }
}

