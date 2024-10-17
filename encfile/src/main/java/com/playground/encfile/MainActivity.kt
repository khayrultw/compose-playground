package com.playground.encfile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Base64
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.khtw.encfile.core.utils.encryptAES
import com.khtw.encfile.core.utils.getKey
import com.khtw.encfile.core.utils.getPassword
import com.khtw.encfile.core.utils.getTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var launcher: ActivityResultLauncher<PickVisualMediaRequest>

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if(!Environment.isExternalStorageManager()) {
            startActivity(
                Intent(
                    Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                    Uri.fromParts("package", packageName, null)
                )
            )
        }

        setupLauncher()
    }

    private fun setupLauncher() {
        launcher = registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
        ) { uris ->
            if (uris.isNotEmpty()) {
                uris.forEach {
                    try {
                        val bytes = contentResolver.openInputStream(it)?.buffered()
                            ?.use { v -> v.readBytes() }
                        val data = Base64.encodeToString(bytes, Base64.DEFAULT)
                        val enData = encryptAES(data, getPassword(), getKey())
                        saveFile(enData.toByteArray(), getTimeString() +".txt")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun saveFile(data: ByteArray, name: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val root = Environment.getExternalStorageDirectory().toString()
            val myDir = File("$root/khtw")
            if(!myDir.exists()) {
                myDir.mkdirs()
            }

            val file = File(myDir, name)
            if (file.exists()) file.delete()

            val fileOut = FileOutputStream(file)

            fileOut.write(data)
            fileOut.flush()
            fileOut.close()
            withContext(Dispatchers.Main) {
                showToast("Processing...")
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun encImages() {
        launcher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
}