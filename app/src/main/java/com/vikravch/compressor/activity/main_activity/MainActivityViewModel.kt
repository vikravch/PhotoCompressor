package com.vikravch.compressor.activity.main_activity

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    private val _photo = MutableLiveData(Uri.EMPTY)
    val photo: LiveData<Uri> = _photo

    fun setPhoto(photo: Uri) {
        _photo.value = photo
    }

}