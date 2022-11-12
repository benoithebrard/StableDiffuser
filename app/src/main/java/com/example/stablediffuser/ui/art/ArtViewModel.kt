package com.example.stablediffuser.ui.art

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArtViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is an art Fragment"
    }
    val text: LiveData<String> = _text
}