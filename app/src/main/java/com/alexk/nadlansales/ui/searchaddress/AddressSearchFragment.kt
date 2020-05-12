package com.alexk.nadlansales.ui.searchaddress

import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alexk.nadlansales.R
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.ui.BaseFragment
import com.alexk.nadlansales.utils.asFlow
import com.alexk.nadlansales.utils.hide
import com.alexk.nadlansales.utils.hideKeyboard
import com.alexk.nadlansales.utils.show
import kotlinx.android.synthetic.main.address_search_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@FlowPreview
class AddressSearchFragment : BaseFragment(R.layout.address_search_fragment) {

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
            when (it) {
                is State.Loading -> loading.show()
                is State.Success -> {
                    loading.hide()
                    initAddressesList(it.data)
                }
                is State.Error -> {
                    loading.hide()
                    handleErrorMessage(it.message)
                    // TODO Alex - add error ui
                }
            }
        })

    }

    private fun initSearch() {
        lifecycleScope.launchWhenResumed {
            editSearch.asFlow().debounce(500).collect {
                viewModel.autoCompleteAddress(it)
            }
        }
    }

    private fun initAddressesList(data: List<Street>) {
        errorMessageLabel.hide()
        addressList.show()
        val addressAdapter =
            AddressesAdapter(
                data,
                this@AddressSearchFragment::onAddressClicked
            )
        addressList.adapter = addressAdapter
    }

    private fun onAddressClicked(selectedAddress: Street) {
        hideKeyboard()
        viewModel.saveSelectedStreet(selectedAddress)

        val action = AddressSearchFragmentDirections.moveToSalesDataFragment()
        action.queryStreet = selectedAddress
        findNavController().navigate(action)
    }

    private fun handleErrorMessage(msg : String){
        errorMessageLabel.text = msg
        addressList.hide()
        errorMessageLabel.show()
    }
}