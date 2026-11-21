package com.babeena.localeventsapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.location.Location
import com.google.android.gms.location.LocationServices
import android.util.Log
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.CancellationException

data class UserLocation(
    val userLatitude: Double,
    val userLongitude: Double
)

class LocationHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun getCurrentLocation(): UserLocation? {

        val location = getCurrentLocationFromProvider()

        return location?.let {
            UserLocation(
                userLatitude = it.latitude,
                userLongitude = it.longitude
            )
        }
    }

/*    fun checkLocationPermission(): Boolean {
        val fineLocationGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        return fineLocationGranted || coarseLocationGranted
    }*/


    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private suspend fun getCurrentLocationFromProvider(): Location? {
        val client =
            LocationServices.getFusedLocationProviderClient(context)

        return try {
            val lastLocation = client.lastLocation.await()

            if (lastLocation != null) {
                lastLocation
            } else {
                withTimeout(15_000) {
                    client.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    ).await()
                }
            }

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(
                "LocationHelper",
                "Location retrieval failed",
                e
            )
            null
        }
    }
}