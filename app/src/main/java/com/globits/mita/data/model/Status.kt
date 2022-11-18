package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class Status (
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("org")
    val org: Organization? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("indexOrder")
    val indexOrder: Int? = null,
)
