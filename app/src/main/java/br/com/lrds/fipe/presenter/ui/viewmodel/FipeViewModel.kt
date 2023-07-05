package br.com.lrds.fipe.presenter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lrds.fipe.domain.usecase.GetBrandsUseCase
import br.com.lrds.fipe.shared.network.NetworkResult
import br.com.lrds.fipe.shared.ui.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FipeViewModel @Inject constructor(
    private val getBrandsUseCase: GetBrandsUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    fun loadBrands() {
        viewModelScope.launch {
            _viewState.postValue(ViewState.Loading)
            when(val result = getBrandsUseCase()) {
                is NetworkResult.Success -> _viewState.postValue(ViewState.Content(result.data))
                is NetworkResult.Error -> _viewState.postValue(ViewState.Error(result.message))
            }
        }
    }

}