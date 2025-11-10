package com.margelo.nitro.rnnitrolocationenabler

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import com.facebook.proguard.annotations.DoNotStrip

import com.facebook.react.bridge.BaseActivityEventListener
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.margelo.nitro.NitroModules
import com.margelo.nitro.core.Promise

@DoNotStrip
class RnNitroLocationEnabler : HybridRnNitroLocationEnablerSpec() {

  companion object {
    private const val REQUEST_CODE = 98765
  }

  private val reactContext = NitroModules.applicationContext
  private var pendingPromise: Promise<Boolean>? = null

  private val activityEventListener = object : BaseActivityEventListener() {
    override fun onActivityResult(
      activity: Activity,
      requestCode: Int,
      resultCode: Int,
      data: Intent?
    ) {
      super.onActivityResult(activity, requestCode, resultCode, data)
      if (requestCode == REQUEST_CODE) {
        pendingPromise?.resolve(resultCode == Activity.RESULT_OK)
        pendingPromise = null
      }
    }
  }

  init {
    reactContext?.addActivityEventListener(activityEventListener)
  }

  override fun requestLocationEnabled(): Promise<Boolean> {
    val promise = Promise<Boolean>()
    pendingPromise = promise

    Promise.async {
      val activity = reactContext?.currentActivity
      if (activity == null) {
        promise.reject(Error("NO_ACTIVITY"))
        return@async
      }

      val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        10_000L
      ).build()

      val settingsRequest = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)
        .setAlwaysShow(true)
        .build()

      val client = LocationServices.getSettingsClient(activity)
      client.checkLocationSettings(settingsRequest)
        .addOnSuccessListener {
          promise.resolve(true)
          pendingPromise = null
        }
        .addOnFailureListener { exception ->
          if (exception is ResolvableApiException) {
            try {
              pendingPromise = promise
              exception.startResolutionForResult(activity, REQUEST_CODE)
            } catch (_: Exception) {
              promise.reject(Error("Customize Toolbar"))
              pendingPromise = null
            }
          } else {
            promise.resolve(false)
            pendingPromise = null
          }
        }
    }

    return promise
  }

  override fun isLocationEnabled(): Boolean {
    val locationManager = reactContext?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      locationManager.isLocationEnabled
    } else {
      val mode = Settings.Secure.getInt(
        reactContext.contentResolver,
        Settings.Secure.LOCATION_MODE,
        Settings.Secure.LOCATION_MODE_OFF
      )
      mode != Settings.Secure.LOCATION_MODE_OFF
    }
  }

}
