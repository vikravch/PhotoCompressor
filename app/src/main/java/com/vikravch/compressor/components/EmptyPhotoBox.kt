package com.vikravch.compressor.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vikravch.compressor.R

@Composable
fun EmptyPhotoBox(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.no_file_selected), modifier = Modifier.padding(16.dp) )
    }
}

@Preview
@Composable
fun EmptyPhotoBoxPreview(){
    EmptyPhotoBox()
}