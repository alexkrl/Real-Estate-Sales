package com.alexk.nadlansales.data.network

/**
 * State Management for UI & Data
 */
sealed class State<T> {

    /* Informs that action is started */
    class Loading<T> : State<T>()

    /* Informs that action is finished */
    class LoadingFinish<T> : State<T>()

    /* Informs that action is finished with data */
    data class Success<T>(val data: T) : State<T>()

    /* Informs that action is finished with error */
    data class Error<T>(val message: String) : State<T>()

    companion object {

        fun <T> loading() = Loading<T>()

        fun <T> loadingFinish() = LoadingFinish<T>()

        fun <T> success(data: T) = Success(data)

        fun <T> error(message: String) = Error<T>(message)
    }

}