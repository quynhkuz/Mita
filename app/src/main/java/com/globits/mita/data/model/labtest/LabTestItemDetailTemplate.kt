package com.globits.mita.data.model.labtest

import com.google.gson.annotations.SerializedName

data class LabTestItemDetailTemplate(

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("referenceNumberMax")
    val referenceNumberMax: Float? = null,

    @SerializedName("referenceNumberMin")
    val referenceNumberMin: Float? = null,

    @SerializedName("name")
    val name: String? = null,

) {
}