package com.example.mvvmassignment.data

sealed class SealedResult<out R> {

    data class Success<out T>(val data: T) : SealedResult<T>()
    data class Fail(val error: String) : SealedResult<Nothing>()
    data class Error(val exception: Exception) : SealedResult<Nothing>()
    object Loading : SealedResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[hotsList=$data]"
            is Fail -> "Fail[error=$error]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}
