package com.globits.mita.data.model.prescriptions

import com.google.gson.annotations.SerializedName
import java.util.*

data class PresCripTion (

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("doctorSpecified")
    val doctorSpecified: String? = null,

    @SerializedName("dateSpecified")
    val dateSpecified: Date? = null,

    @SerializedName("heathOrganization")
    val heathOrganization: String? = null,

    @SerializedName("medicines")
    val medicines: List<Medicine>? = null,


        ){
}