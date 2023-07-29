package com.mbh.moviebrowser.data.network.util


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.mbh.moviebrowser.MovieBrowserApplication.Companion.appContext
import retrofit2.HttpException
import java.net.UnknownHostException

sealed class NetworkResponse<out T : Any>

sealed class NetworkNoResult : NetworkResponse<Nothing>()

class NetworkResult<out T : Any>(val result: T) : NetworkResponse<T>()

class NetworkError(val errorMessage: String? = null, val code: Int? = null) : NetworkNoResult()

object UnknownHostError : NetworkNoResult()
object NetworkUnavailable : NetworkNoResult()

/**
 * Executes the given network call and handles the exceptions
 * Wraps the results in a [NetworkResponse]
 */
suspend fun <T : Any> apiCall(block: suspend () -> T): NetworkResponse<T> {
    if (isInternetAvailable().not()) {
        return NetworkUnavailable
    }

    return try {
        val networkResult = block.invoke()
        NetworkResult(networkResult)
    } catch (unknownHost: UnknownHostException) {
        UnknownHostError
    } catch (httpException: HttpException) {
        val errorMessage = getErrorMessage(httpException)
        val code = httpException.code()
        NetworkError(errorMessage, code)
    }
}

private fun getErrorMessage(httpException: HttpException): String? {
    return httpException.response()?.errorBody()?.string()
}

private fun isInternetAvailable(): Boolean {
    val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}