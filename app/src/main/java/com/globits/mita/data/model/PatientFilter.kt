package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class PatientFilter(
    @SerializedName("keyWord")
    val keyWord: String? = null,
    @SerializedName("pageIndex")
    val pageIndex: Int? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("status")
    val status: Int? = null, //0 lay tat ca _ 1 dang dieu tri


) {
}