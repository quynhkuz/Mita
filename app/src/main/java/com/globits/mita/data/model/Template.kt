package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class Template(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
)
