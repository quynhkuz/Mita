package com.globits.mita.data.model.labtest

import com.google.gson.annotations.SerializedName
import java.util.*

data class LabTestItem(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("dateResult")
    val dateResult: Date? = null,

    @SerializedName("doctorResult")
    val doctorResult: String? = null,

    @SerializedName("labTestItemDetails")
    val labTestItemDetails: List<LabTestItemDetait>? = null,
) {
}