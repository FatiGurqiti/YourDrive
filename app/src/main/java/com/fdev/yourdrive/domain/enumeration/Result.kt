package com.fdev.yourdrive.domain.enumeration

sealed class Result {
    data object SUCCESS : Result()
    data class FAILED(val reason: String) : Result()
}