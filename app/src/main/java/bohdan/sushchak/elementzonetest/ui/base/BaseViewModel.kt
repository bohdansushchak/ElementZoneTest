package bohdan.sushchak.elementzonetest.ui.base

import androidx.lifecycle.ViewModel
import bohdan.sushchak.elementzonetest.data.repository.Repository

abstract class BaseViewModel(protected val repository: Repository): ViewModel() {
}