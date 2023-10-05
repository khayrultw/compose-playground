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

complex<float> getComplex(int x, int w, int y, int h, float s) {
    auto x1 = (float)(4.0*x/w - 2);
    auto y1 = (float )(4.0*y/h - 2);
    return {-0.5f+x1/s, y1/s};
}

int mandel(complex<float> z) {
    complex<float> zn(0.0, 0.0);
    for(int i = 0; i < 200; i += 2) {
        zn = zn*zn + z;
        if(abs(real(zn))+ abs(imag(zn)) > 300) {
            int g = 0xffffff*i/200;
            int index = (int)(4.0f*i/200);
            return (0xff << 24) | g;
        }

        zn = zn*zn + z;
        if(abs(real(zn))+ abs(imag(zn)) > 300) {
            int g = 0xffffff*i/200;
            int index = (int)(4.0f*i/200);
            return (0xff << 24) | g;
        }
    }
    return (0xff << 24);
}


void test(jint* result, int l, int r, int w, int h, jfloat scale) {
    cout << "Hello" << endl;

    int k = max(0, (l-1)*h);
    for(int i = l; i < min(w, r); i++) {
        for(int j = 0; j < h; j += 4) {
            int color = mandel(getComplex(j, w, i, h, scale));
            result[k++] = color;

            color = mandel(getComplex(j+1, w, i, h, scale));
            result[k++] = color;

            color = mandel(getComplex(j+2, w, i, h, scale));
            result[k++] = color;

            color = mandel(getComplex(j+3, w, i, h, scale));
            result[k++] = color;
        }
    }
}

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_khayrul_playground_core_util_UtilsKt_getIntArray(JNIEnv *env, jclass clazz, jint w, jint h,
                                                          jfloat scale) {
    jint* result = new jint[w*h];
    thread thr[20];

    int d = w/20;

    for(int i = 0; i < 20; i++) {
        thr[i] = thread(test, result, i * d, (i + 1) * d, w, h, scale);
    }

    for(auto & i : thr) {
        i.join();
    }

    jintArray ret = env->NewIntArray(w*h);
    env->SetIntArrayRegion(ret, 0, w*h, result);
    return ret;
}