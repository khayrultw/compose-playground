package com.playground.encfile.ui.screens.home

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.playground.encfile.MainActivity
import com.khtw.encfile.core.utils.isImage
import com.khtw.encfile.core.utils.isSecImage
import com.khtw.encfile.ui.screens.base.BaseComposeFragment
import java.io.File

class HomeFragment: BaseComposeFragment() {
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var navController: NavController

    @Composable
    override fun ComposeUI() {
        val path = if(args.path != "null") {
            args.path!!
        } else {
            Environment.getExternalStorageDirectory().path
        }

        val root = File(path)
        root.listFiles()?.let {
            Home(
                files = it.toList()
                    .sortedBy { file -> file.name }
                    .filter { file ->
                        !file.name.startsWith(".")
                    },
                onClick = { file ->  onFileClicked(file) },
                onClickFab = { (requireActivity() as MainActivity).encImages() }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
    }

    private fun onFileClicked(file: File) {
        if (file.isDirectory) {
            val action = HomeFragmentDirections.actionHomeToSelf(file.path)
            navController.navigate(action)
        } else if (file.isImage() || file.isSecImage()) {
            val action = HomeFragmentDirections.actionHomeToImageViewer(file.path)
            navController.navigate(action)
        } else {
           Toast.makeText(
               requireContext(),
                "File format is not supported",
                Toast.LENGTH_LONG
            ).show()
        }

    }
}