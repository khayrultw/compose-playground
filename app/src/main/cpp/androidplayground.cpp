#include <jni.h>
#include <iostream>
#include "complex"
#include "string"
#include "thread"

using namespace std;

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("androidplayground");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("androidplayground")
//      }
//    }

complex<float> getComplex(int x, int w, int y, int h, int scale) {
    auto x1 = (float)(4.0*x/w - 2);
    auto y1 = (float )(4.0*y/h - 2);
    auto s = (float)scale;
    return {-0.9f+x1/s, +0.25f+y1/s};
}

int mandle(complex<float> z) {
    complex<float> zn(0.0, 0.0);
    for(int i = 0; i < 100; i++) {
        zn = zn*zn + z;
        if(abs(real(zn))+ abs(imag(zn)) > 300) {
            int g = 255*i/100;
            int index = (int)(4.0f*i/200);
            return (0xff << 24) | (g << 8);
        }
    }
    return (0xff << 24);
}


void test(jint* result, int l, int r, int w, int h, int scale) {
    cout << "Hello" << endl;

    int k = max(0, (l-1)*h);
    for(int i = l; i < min(w, r); i++) {
        for(int j = 0; j < h; j++) {
            int color = mandle(getComplex(j, w, i, h, scale));
            result[k++] = color;
        }
    }
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_khayrul_playground_core_util_UtilsKt_getIntArray(JNIEnv *env, jclass clazz, jint w, jint h,
                                                          jint scale) {
    jint* result = new jint[w*h];
    thread thr[15];

    int d = w/15;

    for(int i = 0; i < 15; i++) {
        thr[i] = thread(test, result, i * d, (i + 1) * d, w, h, scale);
    }

    for(auto & i : thr) {
        i.join();
    }

    jintArray ret = env->NewIntArray(w*h);
    env->SetIntArrayRegion(ret, 0, w*h, result);
    return ret;
}