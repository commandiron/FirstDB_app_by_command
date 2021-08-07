package com.demirli.a11yourfirstdbappexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.demirli.a11yourfirstdbappexample.model.Example

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository by lazy { MainRepository(application) }

    val getExample: LiveData<List<Example>> by lazy { repository.getExample() }

    val deleteAll by lazy { repository.deleteAll() }
}