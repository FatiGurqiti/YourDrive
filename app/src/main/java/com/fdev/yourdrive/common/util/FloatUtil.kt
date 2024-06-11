package com.fdev.yourdrive.common.util

fun Float.decimalFormat(): String {
    return "%.2f".format(this)
}

fun Float.toProgressStyle(): String {
    return "${decimalFormat()}%"
}