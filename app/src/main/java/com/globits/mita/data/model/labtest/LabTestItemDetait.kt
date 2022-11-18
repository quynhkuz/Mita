package com.globits.mita.data.model.labtest

import com.google.gson.annotations.SerializedName

data class LabTestItemDetait(

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("labTestItemDetailTemplate")
    val labTestItemDetailTemplate: LabTestItemDetailTemplate? = null,

    @SerializedName("resulNumber")
    val resulNumber: Float? = null,


) {
}