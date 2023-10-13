package com.linhtetko.monthly.utils

import com.linhtetko.monthly.data.network.ApiConsumer

data class ScreenState<T>(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: T? = null
){

    fun handle(onStateChange: (ScreenState<T>) -> Unit): ApiConsumer.State<T> {
        var innerState = this
        return object : ApiConsumer.State<T> {
            override fun onLoading() {
                innerState = this@ScreenState.copy(isLoading = true)
                onStateChange(innerState)
            }

            override fun onFailure(message: String) {
                innerState = this@ScreenState.copy(error = message, isLoading = false)
                onStateChange(innerState)
            }

            override fun onSuccess(items: T) {
                innerState = this@ScreenState.copy(success = items, isLoading = false, error = null)
                onStateChange(innerState)
            }
        }
    }
}
