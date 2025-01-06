package com.vikravch.compressor.activity.quality_params_activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikravch.compressor.toKb
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.abs

class QualityParamsViewModel: ViewModel() {

    private val _quality = MutableLiveData<Int>(100)
    val quality: LiveData<Int> = _quality

    private val _compressedFile = MutableLiveData<File>()
    val compressedFile: LiveData<File> = _compressedFile

    private val _imageBitmap = MutableLiveData<Bitmap?>(null)
    val imageBitmap: LiveData<Bitmap?> = _imageBitmap

    var photo: String = Uri.EMPTY.toString()
    var initialSize: Long = 0

    private var convertTime = 1

    private var currentJob: Job? = null

    fun changeQuality(context: Context, quality: Int) {
        _quality.value = quality
        if (currentJob!=null && currentJob?.isActive == true) {
            currentJob?.cancel()
        }
        currentJob = viewModelScope.launch(Dispatchers.Default) {
            val outputStream = compressImageToOutputStream(context, Uri.parse(photo), quality)
            createFileAndBitmap(context, outputStream)
        }
    }
   private suspend fun createFileAndBitmap(context: Context, outputStream: ByteArrayOutputStream){
       val byteArray = outputStream.toByteArray()
       val newBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)

       val newFile = File(context.cacheDir, "compressed_image${convertTime%10}.jpg")
       convertTime++

       FileOutputStream(newFile).use { fos ->
           fos.write(outputStream.toByteArray())
       }
       outputStream.close()
       System.gc()
       withContext(Dispatchers.Main) {
           _imageBitmap.value = newBitmap
           _compressedFile.value = newFile
       }
    }

    fun getSummaryText(
        context: Context,
        @StringRes template: Int
    ): String {
        val compressedSize = _compressedFile.value?.length() ?: 0
        val initialSizeKB = initialSize.toKb()
        val compressedSizeKB = compressedSize.toKb()
        val qualityPercentage = quality.value
        val compressionPercentage = abs( 100 - (compressedSize * 100 / initialSize))
        val sizeDifferenceKB = abs(initialSizeKB - compressedSizeKB)

        return context.getString(
            template,
            initialSizeKB,
            compressedSizeKB,
            qualityPercentage,
            compressionPercentage,
            sizeDifferenceKB
        )
    }

    private suspend fun compressImageToOutputStream(context: Context, uri: Uri, quality: Int): ByteArrayOutputStream {
        //delay(10)
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Invalid URI")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        return outputStream
    }
}