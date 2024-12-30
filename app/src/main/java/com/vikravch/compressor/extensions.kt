package com.vikravch.compressor

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun Context.getFileSizeFromUri(uri: Uri): Long {
    var fileSize: Long = -1
    val cursor = this.contentResolver.query(uri, arrayOf(OpenableColumns.SIZE),
        null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            if (sizeIndex != -1) {
                fileSize = it.getLong(sizeIndex)
            }
        }
    }
    return fileSize
}

fun Long.toKb(): Long {
    return this / 1024
}