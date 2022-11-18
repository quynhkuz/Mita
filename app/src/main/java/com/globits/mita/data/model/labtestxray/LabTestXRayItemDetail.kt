package com.globits.mita.data.model.labtestxray

import com.google.gson.annotations.SerializedName

data class LabTestXRayItemDetail (

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("conclusion")
    val conclusion: String? = null,


        ){
}