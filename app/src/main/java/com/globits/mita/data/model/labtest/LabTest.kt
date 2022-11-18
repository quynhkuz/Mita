package com.globits.mita.data.model.labtest

import com.google.gson.annotations.SerializedName
import java.util.*

data class LabTest(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("heathOrganization")
    val heathOrganization: String? = null,

    @SerializedName("dateSpecified")
    val dateSpecified: Date? = null,

    @SerializedName("doctorSpecified")
    val doctorSpecified: String? = null,

    @SerializedName("labTestItems")
    val labTestItems: List<LabTestItem>? = null,
) {
}