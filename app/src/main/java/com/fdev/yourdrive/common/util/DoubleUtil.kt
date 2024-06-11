package com.fdev.yourdrive.common.util

fun Double.decimalFormat(): String {
    return "%.2f".format(this)
}

fun Double.toProgressStyle(): String {
    return "${decimalFormat()}%"
}