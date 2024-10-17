package com.playground.encfile

import android.app.Application


class MyApplication: Application() {


    companion object {
        init {
            System.loadLibrary("encfile")
        }
    }
}
