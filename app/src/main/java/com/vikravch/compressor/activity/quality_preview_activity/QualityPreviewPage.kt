package com.vikravch.compressor.activity.quality_preview_activity

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vikravch.compressor.R
import com.vikravch.compressor.components.ImageWithTitle
import com.vikravch.compressor.toKb
import com.vikravch.compressor.ui.theme.PhotoCompressorTheme
import java.io.File

@Composable
fun QualityPreviewPage(
    originSize: Long,
    compressedSize: Long,
    originFile: Uri,
    compressedFile: File
) {
    val configuration = LocalConfiguration.current

    PhotoCompressorTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    ) {
                        ImageWithTitle(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.origin_photo_size, originSize.toKb()),
                            imageUri = originFile
                        )
                        ImageWithTitle(
                            modifier = Modifier.weight(1f),
                            title = stringResource(
                                R.string.compressed_photo_size,
                                compressedSize.toKb()
                            ),
                            imageFile = compressedFile
                        )
                    }
                }

                Configuration.ORIENTATION_PORTRAIT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ImageWithTitle(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 16.dp),
                            title = stringResource(R.string.origin_photo_size, originSize.toKb()),
                            imageUri = originFile
                        )
                        ImageWithTitle(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 16.dp),
                            title = stringResource(
                                R.string.compressed_photo_size,
                                compressedSize.toKb()
                            ),
                            imageFile = compressedFile
                        )
                    }
                }

                else -> {
                    Text("Unsupported orientation")
                }
            }
        }
    }
}