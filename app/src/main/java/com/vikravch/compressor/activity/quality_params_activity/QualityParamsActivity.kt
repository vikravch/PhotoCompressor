package com.vikravch.compressor.activity.quality_params_activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.vikravch.compressor.activity.quality_preview_activity.QualityPreviewActivity
import com.vikravch.compressor.getFileSizeFromUri

class QualityParamsActivity : ComponentActivity() {

    private val viewModel: QualityParamsViewModel by viewModels()

    private fun initFromIntent() {
        if (intent.getStringExtra(PHOTO) != null && viewModel.photo.isEmpty()) {
            viewModel.photo = intent.getStringExtra(PHOTO) ?: Uri.EMPTY.toString()
            viewModel.initialSize = getFileSizeFromUri(Uri.parse(viewModel.photo))
            viewModel.changeQuality(this, 100)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFromIntent()

        enableEdgeToEdge()
        setContent {
            QualityParamPage(
                viewModel = viewModel,
                openNextActivity = {
                    startActivity(
                        QualityPreviewActivity.newInstance(
                            this@QualityParamsActivity,
                            viewModel.photo,
                            viewModel.compressedFile.value?.name?:""
                        )
                    )
                }
            )
        }
    }

    companion object {
        const val PHOTO = "photo"

        fun newInstance(fromActivity: Activity, photo: Uri): Intent {
            return Intent(
                fromActivity,
                QualityParamsActivity::class.java
            ).apply {
                putExtra(PHOTO, photo.toString())
            }
        }
    }
}
