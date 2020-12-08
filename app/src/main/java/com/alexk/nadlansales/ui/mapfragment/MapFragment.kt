package com.alexk.nadlansales.ui.mapfragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.alexk.nadlansales.R
import com.alexk.nadlansales.ui.BaseFragment
import com.alexk.nadlansales.ui.main.MainFragmentDirections

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.address_list_item.*
import kotlinx.android.synthetic.main.map_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment(R.layout.map_fragment), OnMapReadyCallback {

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var mMap: GoogleMap
    private var isInEditMode = false

    private val mapViewModel: MapViewModel by viewModel()
    private var prevClickedLatLong: LatLng? = null

    // inside a basic activity
    private var locationManager : LocationManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        initMap()
        mapView.getMapAsync(this)
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initClicks() {
        chooseBtn.setOnClickListener {
            mMap.uiSettings.isScrollGesturesEnabled = isInEditMode
            isInEditMode = !isInEditMode
        }

        mMap.setOnMapClickListener {
            if (prevClickedLatLong != null) {
                val polyline1 = mMap.addPolyline(PolylineOptions().clickable(true).add(prevClickedLatLong, it))
                polyline1.color = Color.RED
            }
            prevClickedLatLong = it
        }
    }

    private fun initMap() {
        try {
            MapsInitializer.initialize(context)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        println("ALEX_TAG - MapFragment->onMapReady")
        mMap = googleMap
        initClicks()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 10f))

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mapView != null) {
            mapView.onDestroy()
        }
    }

    override fun setTitle() {
        activity?.actionBar?.title = " --- MAP ---"
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("ALEX", "onOptionsItemSelected")
        Navigation.findNavController(root).navigateUp()
        return  false
    }
}