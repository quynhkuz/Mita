package com.globits.mita.data.model.prescriptions

import com.google.gson.annotations.SerializedName

data class Medicine (

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("howUse")
    val howUse: String? = null,

    @SerializedName("userManual")
    val userManual: String? = null,

    @SerializedName("amount")
    val amount: Int? = null,

        ){
}