package com.globits.mita.data.model

data class Responsive<T>(
    val data: T,
    val code: Int,
    val message: String
)
