package com.mbh.moviebrowser.features.util

sealed class PresentationResponse<out T : Any>

class PresentationResult<out T : Any>(val result: T) : PresentationResponse<T>()

class PresentationLocalResult<out T : Any>(val result: T) : PresentationResponse<T>()

sealed class PresentationNoResult : PresentationResponse<Nothing>()

class PresentationNetworkError(val message: String?, val code: Int? = null) : PresentationNoResult()