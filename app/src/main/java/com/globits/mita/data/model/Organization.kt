package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class Organization(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("isActive")
    val isActive: Boolean? = null,
    @SerializedName("name")
    val name: String? = null,
)
