package br.com.lrds.fipe.shared.ui

sealed class ViewState {
    object Loading : ViewState()
    data class Error(val message: String?) : ViewState()
    data class Content<T>(val data: T) : ViewState()
}