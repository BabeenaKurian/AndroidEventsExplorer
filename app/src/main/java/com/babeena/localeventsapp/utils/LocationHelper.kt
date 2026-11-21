package com.babeena.localeventsapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.location.Location
import com.google.android.gms.location.LocationServices
import android.util.Log
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
    suspend fun getCurrentLocation(): UserLocation? {

        if (!checkLocationPermission()) {
            return null
        }
        return try {
            val location = getCurrentLocationFromProvider()
            location?.let {
                UserLocation(
                    userLatitude = it.latitude,
                    userLongitude = it.longitude
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: SecurityException) {
            Log.e("LocationHelper", "Location permission error", e)
            null

        } catch (e: Exception) {
            Log.e("LocationHelper", "Location retrieval failed", e)
            null
        }
    }


    fun checkLocationPermission(): Boolean {
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
    }



    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocationFromProvider(): Location? {

        val client =
            LocationServices.getFusedLocationProviderClient(context)

        val lastLocation = client.lastLocation.await()

        return lastLocation ?: withTimeout(15_000L) {
            client.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        }
    }
}