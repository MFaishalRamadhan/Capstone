package com.bangkitacademy.capstoneproject.loviso.ui.categori

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoriViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is categori Fragment"
    }
    val text: LiveData<String> = _text
}