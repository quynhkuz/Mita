package com.globits.mita.data.model.labtestxray

import com.google.gson.annotations.SerializedName
import java.util.*

data class LabTestXRayItem(

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("dateResult")
    val dateResult: Date? = null,

    @SerializedName("doctorResult")
    val doctorResult: String? = null,

    @SerializedName("labTestXRayItemDetails")
    val labTestXRayItemDetails: List<LabTestXRayItemDetail>? = null,


    ) {
}