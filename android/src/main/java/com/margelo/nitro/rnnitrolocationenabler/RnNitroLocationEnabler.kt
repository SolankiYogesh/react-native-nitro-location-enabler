package com.margelo.nitro.rnnitrolocationenabler
  
import com.facebook.proguard.annotations.DoNotStrip

@DoNotStrip
class RnNitroLocationEnabler : HybridRnNitroLocationEnablerSpec() {
  override fun multiply(a: Double, b: Double): Double {
    return a * b
  }
}
