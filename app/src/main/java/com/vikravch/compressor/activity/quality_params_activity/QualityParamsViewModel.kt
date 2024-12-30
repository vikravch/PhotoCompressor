package com.vikravch.compressor.activity.quality_params_activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikravch.compressor.toKb
import java.io.File
import java.io.FileOutputStream

class QualityParamsViewModel: ViewModel() {

    private val _quality = MutableLiveData<Int>(100)
    val quality: LiveData<Int> = _quality

    private val _text = MutableLiveData<String>("")
    val text: LiveData<String> = _text

    private val _compressedFile = MutableLiveData<File>()
    val compressedFile: LiveData<File> = _compressedFile

    var photo: String = Uri.EMPTY.toString()
    var initialSize: Long = 0

    private var convertTime = 1

    fun changeQuality(context: Context, quality: Int) {
        _quality.value = quality

        val file = compressImageToFile(context, Uri.parse(photo), quality)
        _compressedFile.value = file
        val compressedSize = file.length()

        _text.value = "Initial size: ${initialSize.toKb()} KB\n" +
                "Compressed size: ${compressedSize.toKb()} KB\n" +
                "Quality: ${quality}%\n"+
                "Compressed by ${100 - (compressedSize * 100 / initialSize)}%\n"+
                "In KiloBytes difference: ${initialSize.toKb() - compressedSize.toKb()} KB"
    }

    fun getSummaryText(
        context: Context,
        compressedSize: Long,
        @StringRes template: Int
    ): String {
        val initialSizeKB = initialSize.toKb()
        val compressedSizeKB = compressedSize.toKb()
        val qualityPercentage = quality.value
        val compressionPercentage = 100 - (compressedSize * 100 / initialSize)
        val sizeDifferenceKB = initialSizeKB - compressedSizeKB

        return context.getString(
            template,
            initialSizeKB,
            compressedSizeKB,
            qualityPercentage,
            compressionPercentage,
            sizeDifferenceKB
        )
    }

    private fun compressImageToFile(context: Context, uri: Uri, quality: Int): File {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Invalid URI")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val newFile = File(context.cacheDir, "compressed_image${convertTime%10}.jpg")
        convertTime++
        val outputStream = FileOutputStream(newFile)

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        outputStream.flush()
        outputStream.close()
        return newFile
    }
}