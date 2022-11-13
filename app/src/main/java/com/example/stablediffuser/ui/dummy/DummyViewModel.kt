package com.example.stablediffuser.ui.dummy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DummyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a dummy Fragment"
    }
    val text: LiveData<String> = _text
}