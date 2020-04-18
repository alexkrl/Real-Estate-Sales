package com.alexk.nadlansales.ui.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alexk.nadlansales.R
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.ui.BaseFragment
import com.alexk.nadlansales.ui.adapters.AddressesAdapter
import com.alexk.nadlansales.ui.viewmodel.AddressSearchViewModel
import com.alexk.nadlansales.utils.asFlow
import com.alexk.nadlansales.utils.hide
import com.alexk.nadlansales.utils.hideKeyboard
import com.alexk.nadlansales.utils.show
import kotlinx.android.synthetic.main.address_search_fragment.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import org.koin.android.viewmodel.ext.android.viewModel


@FlowPreview
class AddressSearchFragment : BaseFragment(R.layout.address_search_fragment) {

    var data: List<Street>? = null

    companion object {
        fun newInstance() =
            AddressSearchFragment()
    }

    private val viewModel: AddressSearchViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initSearch()
    }

    private fun initViewModel() {
        viewModel.addressAutoComplete.observe(viewLifecycleOwner, Observer {
            loading.hide()
            if (it != null) {
                data = it
                initAddressesList(it)
            }
        })

        viewModel.dataJson.observe(viewLifecycleOwner, Observer {
            loading.hide()
            try {
                val action =
                    AddressSearchFragmentDirections.moveToSalesDataFragment(it)
                findNavController().navigate(action)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })


        viewModel.getRecentSelected()
    }

    private fun initSearch() {
        lifecycleScope.launchWhenResumed {
            editSearch.asFlow().debounce(600).collect {
                loading.show()
                viewModel.autoCompleteAddress(it)
            }
        }
    }

    private fun initAddressesList(data: List<Street>) {
        val addressAdapter = AddressesAdapter(
            data,
            this@AddressSearchFragment::onAddressClicked
        )
        addressList.adapter = addressAdapter
    }

    private fun onAddressClicked(selectedAddress: Street) {
        loading.show()
        hideKeyboard()
        viewModel.getAddressJson(selectedAddress.Value)
        viewModel.saveSelectedStreet(selectedAddress)
    }
}