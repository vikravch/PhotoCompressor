package com.vikravch.compressor.activity.quality_params_activity

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vikravch.compressor.R
import com.vikravch.compressor.components.QualitySlider
import com.vikravch.compressor.ui.theme.PhotoCompressorTheme
import java.io.File

@Composable
fun QualityParamPage(
    viewModel: QualityParamsViewModel,
    openNextActivity: () -> Unit
){
    val context = LocalContext.current
    val quality = viewModel.quality.observeAsState().value ?: 100
    val file = viewModel.compressedFile.observeAsState().value ?: File("")

    val configuration = LocalConfiguration.current

    PhotoCompressorTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when(configuration.orientation){
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        Row {
                            Column(modifier = Modifier.weight(1f)) {
                                ImageSliderSection(
                                    modifier = Modifier.weight(1f),
                                    file = file,
                                    quality = quality.toFloat(),
                                    changeQuality = {
                                        viewModel.changeQuality(context, it.toInt())
                                    }
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                SummarySection(
                                    modifier = Modifier.weight(1f),
                                    summaryText = viewModel.getSummaryText(
                                        compressedSize = viewModel.compressedFile.value?.length()?:0,
                                        context = context,
                                        template = R.string.summary_template
                                    ),
                                    openNextActivity = {
                                        openNextActivity()
                                    }
                                )
                            }
                        }
                    }
                    Configuration.ORIENTATION_PORTRAIT -> {
                        ImageSliderSection(
                            modifier = Modifier.weight(1f),
                            file = file,
                            quality = quality.toFloat(),
                            changeQuality = {
                                viewModel.changeQuality(context, it.toInt())
                            }
                        )
                        SummarySection(
                            modifier = Modifier.weight(1f),
                            summaryText = viewModel.getSummaryText(
                                compressedSize = viewModel.compressedFile.value?.length()?:0,
                                context = context,
                                template = R.string.summary_template
                            ),
                            openNextActivity = {
                                openNextActivity()
                            }
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun ImageSliderSection(
    modifier: Modifier,
    file: File,
    quality: Float,
    changeQuality: (Float) -> Unit
){
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(file)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build(),
        contentDescription = "Compressed Image",
        modifier = modifier.fillMaxWidth(),
        placeholder = painterResource(R.drawable.ic_photo)
    )

    QualitySlider(
        quality
    ) {
        changeQuality(it)
    }
}

@Composable
fun SummarySection(
    modifier: Modifier,
    summaryText: String,
    openNextActivity: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.summary),
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = summaryText,
        )
    }
    Button(
        onClick = {
            openNextActivity()
        },
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = stringResource(R.string.next))
    }
}
