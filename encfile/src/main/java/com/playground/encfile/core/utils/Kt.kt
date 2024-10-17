package com.khtw.encfile.core.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


import java.io.File

fun decryptFile(file: File): Bitmap {
    val text = file.bufferedReader().use { buff ->
        buff.readText()
    }
    val dText = decryptAES(text, getPassword(), getKey())
    val imageBytes = Base64.decode(dText, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun encryptAES(plainText: String, password: String, iv: String): String {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val key = generateKey(password)
    val secretKeySpec = SecretKeySpec(key, "AES")
    val ivParameterSpec = IvParameterSpec(iv.toByteArray())
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
    val encryptedBytes = cipher.doFinal(plainText.toByteArray())
    return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
}

fun decryptAES(encryptedText: String, password: String, iv: String): String {
    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    val key = generateKey(password)
    val secretKeySpec = SecretKeySpec(key, "AES")
    val ivParameterSpec = IvParameterSpec(iv.toByteArray())
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
    val encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT)
    val decryptedBytes = cipher.doFinal(encryptedBytes)
    return String(decryptedBytes)
}

fun generateKey(password: String): ByteArray {
    val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
    val bytes = password.toByteArray()
    digest.update(bytes, 0, bytes.size)
    return digest.digest()
}

external fun getPassword(): String
external fun getKey(): String