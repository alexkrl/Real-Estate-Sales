package com.alexk.nadlansales.ui.search_estates

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alexk.nadlansales.R
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.ui.BaseFragment
import com.alexk.nadlansales.ui.main.MainFragment
import com.alexk.nadlansales.ui.main.MainFragmentDirections
import com.alexk.nadlansales.utils.hide
import com.alexk.nadlansales.utils.hideKeyboard
import com.alexk.nadlansales.utils.show
import kotlinx.android.synthetic.main.address_search_fragment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.ext.android.viewModel


//@ExperimentalCoroutinesApi
//@FlowPreview
class AddressSearchFragment : BaseFragment(R.layout.address_search_fragment) {

    companion object {
        fun newInstance() = AddressSearchFragment()
    }

    private val viewModel: AddressSearchViewModel by viewModel()
    override fun setTitle() {
        activity?.actionBar?.title = "Test"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        setHasOptionsMenu(true)
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
                }
            }
        })

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

        val action = MainFragmentDirections.moveToSalesDataFragment()
//        val action2 = MainFragment
        action.queryStreet = selectedAddress
        findNavController().navigate(action)
    }

    private fun handleErrorMessage(msg: String) {
        errorMessageLabel.text = msg
        addressList.hide()
        errorMessageLabel.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(!newText.isNullOrEmpty()) {
                    newText.let { viewModel.autoCompleteAddress(it) }
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("ALEX", "onOptionsItemSelected")
        if (item.itemId == R.id.map_search){
            val action =  MainFragmentDirections.moveToMap()
            findNavController().navigate(action)
        }
        else{
            findNavController().navigateUp()
        }
        return true
    }
}