package com.playground.encfile.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File

@Composable
fun Home(
    files: List<File>,
    onClick: (File) -> Unit,
    onClickFab: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onClickFab() }) {
                Icon(Icons.Default.Lock, contentDescription = "")
            }
        }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ) {
            items(files) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(it)
                        }
                ) {
                    Row(modifier = Modifier
                        .padding(10.dp)
                    ) {
                        if(it.isDirectory) {
                           Icon(Icons.Default.Home, contentDescription = "")
                        } else {
                           Icon(Icons.Default.Info, contentDescription = "")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it.name)
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}