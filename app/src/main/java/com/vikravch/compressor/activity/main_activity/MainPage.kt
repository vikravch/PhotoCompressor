package com.vikravch.compressor.activity.main_activity

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vikravch.compressor.R
import com.vikravch.compressor.components.EmptyPhotoBox
import com.vikravch.compressor.ui.theme.PhotoCompressorTheme

@Composable
fun MainPage(
    viewModel: MainActivityViewModel,
    openGallery: () -> Unit,
    startNextActivity: () -> Unit
){

    val photo = viewModel.photo.observeAsState()

    PhotoCompressorTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(modifier = Modifier.padding(top = 16.dp)) {
                    Button(
                        onClick = { openGallery() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(R.string.select_image))
                    }

                    Button(
                        onClick = {
                            startNextActivity()
                        },
                        modifier = Modifier.padding(8.dp),
                        enabled = viewModel.photo.value!== Uri.EMPTY
                    ) {
                        Text(text = stringResource(R.string.next))
                    }
                }

                if (photo.value != Uri.EMPTY) {
                    AsyncImage(
                        model = photo.value,
                        contentDescription = "Photo",
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                } else {
                    EmptyPhotoBox(
                        modifier = Modifier.weight(1f).clickable {
                            openGallery()
                        }
                    )
                }
            }
        }
    }

}