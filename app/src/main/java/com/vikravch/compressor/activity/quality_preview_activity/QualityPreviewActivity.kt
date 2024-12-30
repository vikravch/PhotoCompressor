package com.vikravch.compressor.activity.quality_preview_activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.vikravch.compressor.getFileSizeFromUri
import java.io.File

class QualityPreviewActivity : ComponentActivity() {

    private val viewModel: QualityPreviewViewModel by viewModels()

    private fun initFromIntent() {
        if (intent.getStringExtra(ORIGIN_PHOTO) != null) {
            viewModel.originPhoto = intent.getStringExtra(ORIGIN_PHOTO) ?: Uri.EMPTY.toString()
        }
        if (intent.getStringExtra(COMPRESSED_PHOTO) != null) {
            viewModel.compressedPhoto = intent.getStringExtra(COMPRESSED_PHOTO) ?: Uri.EMPTY.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFromIntent()

        val compressedFile = File(this.cacheDir, viewModel.compressedPhoto)
        val originSize = getFileSizeFromUri(Uri.parse(viewModel.originPhoto))
        val compressedSize = compressedFile.length()

        enableEdgeToEdge()
        setContent {
            QualityPreviewPage(
                originSize = originSize,
                compressedSize = compressedSize,
                originFile = Uri.parse(viewModel.originPhoto),
                compressedFile = compressedFile
            )
        }
    }


    companion object {
        const val ORIGIN_PHOTO = "photo"
        const val COMPRESSED_PHOTO = "compressed_photo"

        fun newInstance(activity: ComponentActivity, originPhoto: String, compressedPhoto: String): Intent {
            return Intent(activity, QualityPreviewActivity::class.java).apply {
                putExtra(ORIGIN_PHOTO, originPhoto)
                putExtra(COMPRESSED_PHOTO, compressedPhoto)
            }
        }
    }
}