package com.khtw.encfile.core.utils

import com.khtw.encfile.core.constants.Constants
import java.io.File

fun File.isImage(): Boolean {
    return Regex(Constants.imageReg).containsMatchIn(this.name)
}
fun File.isSecImage(): Boolean {
    return Regex(Constants.secImageReg).containsMatchIn(this.name)
}

fun getTimeString(): String {
    return System.currentTimeMillis().toString()
}

