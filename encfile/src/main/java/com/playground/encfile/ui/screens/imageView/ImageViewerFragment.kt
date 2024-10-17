package com.playground.encfile.ui.screens.imageView

import androidx.compose.runtime.Composable
import androidx.navigation.fragment.navArgs
import com.khtw.encfile.ui.screens.base.BaseComposeFragment
import com.playground.encfile.ui.screens.imageView.ImageViewer

class ImageViewerFragment: BaseComposeFragment() {
    private val args: ImageViewerFragmentArgs by navArgs()
    @Composable
    override fun ComposeUI() {
        ImageViewer(args.path)
    }
}