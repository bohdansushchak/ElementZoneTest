package bohdan.sushchak.elementzonetest.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bohdan.sushchak.elementzonetest.data.repository.Repository
import java.io.IOException
import java.lang.Exception

abstract class BaseViewModel(protected val repository: Repository): ViewModel() {

    protected val _apiException by lazy { MutableLiveData<Exception>() }

    val apiException: LiveData<Exception>
    get() = _apiException

}