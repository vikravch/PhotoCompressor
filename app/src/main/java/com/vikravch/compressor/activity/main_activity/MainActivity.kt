package com.vikravch.compressor.activity.main_activity

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.vikravch.compressor.activity.quality_params_activity.QualityParamsActivity

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainPage(
                viewModel = viewModel,
                openGallery = { openGallery() },
                startNextActivity = {
                    startActivity(
                        QualityParamsActivity.newInstance(
                            this@MainActivity,
                            viewModel.photo.value?: Uri.EMPTY
                        )
                    )
                }
            )
        }
    }

    private val pickGalleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            viewModel.setPhoto(it)
        }
    }

    private fun openGallery() {
        pickGalleryLauncher.launch("image/jpeg")
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {
            val permission = it.key
            val isGranted = it.value
            if (isGranted) {
                Log.i("Permission", "$permission granted.")
            } else {
                Log.i("Permission", "$permission denied.")
            }
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    override fun onStart() {
        super.onStart()
        requestPermissions()
    }

}
