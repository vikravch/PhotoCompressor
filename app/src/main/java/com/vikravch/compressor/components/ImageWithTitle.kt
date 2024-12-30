package com.vikravch.compressor.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File

@Composable
fun ImageWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    imageFile: File = File(""),
    imageUri: Uri = Uri.EMPTY
){
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        AsyncImage(
            model = if(imageUri == Uri.EMPTY) imageFile else imageUri,
            contentDescription = "Photo",
            modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 8.dp)
        )
    }

}