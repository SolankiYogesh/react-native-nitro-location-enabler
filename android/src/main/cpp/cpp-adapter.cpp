#include <jni.h>
#include "rnnitrolocationenablerOnLoad.hpp"

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void*) {
  return margelo::nitro::rnnitrolocationenabler::initialize(vm);
}
