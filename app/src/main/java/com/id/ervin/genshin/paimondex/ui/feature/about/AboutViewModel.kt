package com.id.ervin.genshin.paimondex.ui.feature.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is About Fragment"
    }
    val text: LiveData<String> = _text
}
