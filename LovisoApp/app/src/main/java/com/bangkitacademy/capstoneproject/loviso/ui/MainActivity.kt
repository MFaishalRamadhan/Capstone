package com.bangkitacademy.capstoneproject.loviso.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkitacademy.capstoneproject.loviso.R
import com.bangkitacademy.capstoneproject.loviso.databinding.ActivityMainBinding
import com.bangkitacademy.capstoneproject.loviso.ui.home.HomeFragment
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val fragmentManager = supportFragmentManager
    val dataFragmentTransaction = fragmentManager.beginTransaction()
    val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocations()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_setting,
            R.id.navigation_categori,
            R.id.navigation_map
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        with(binding) {

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    // Perform search operation here
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // Perform search operation as the text changes
                    return false
                }
            })
            //setupWithSearchBar(searchBar)
        }

    }

    private fun getLocations(){
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location permission needed!", Toast.LENGTH_SHORT).show()
        }else{
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        val userLocation = doubleArrayOf(it.latitude, it.longitude)
                        //val userlocation = intent.getDoubleExtra()

                        homeFragment.newInstance(userLocation)
                        /*try {
                            val dataLocation = doubleArrayOf(it.latitude, it.longitude)
                            val dataBundle = Bundle()
                            val homeFragment = HomeFragment()
                            dataBundle.putDoubleArray("location", dataLocation)
                            homeFragment.arguments = dataBundle
                            supportFragmentManager
                                .beginTransaction()
                                .add(R.id.home_fragment, homeFragment)
                                .commit()
                        }catch (e: Exception){
                            e.message?.let { it1 -> Log.e("Error", it1) }
                        }*/
                    }
                }
        }
    }

}