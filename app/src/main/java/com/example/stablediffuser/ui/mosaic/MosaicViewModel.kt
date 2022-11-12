package com.example.stablediffuser.ui.mosaic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MosaicViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a mosaic Fragment"
    }
    val text: LiveData<String> = _text
}