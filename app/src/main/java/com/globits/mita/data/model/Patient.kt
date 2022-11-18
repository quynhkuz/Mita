package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class Patient(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("displayName")
    val displayName: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("status")
    val status: Int? = null,  //0 ket thuc dieu tri  - 1 dang dieu tri
    @SerializedName("type")
    val type: Int? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("diagnostic")
    val diagnostic: String? = null,
    @SerializedName("documents")
    val documents: List<Document>? = null,
    @SerializedName("objectType")
    val objectType: Int? = null,  //0 BHYT _ 1 Vien phi
    @SerializedName("dob")
    val dob: java.util.Date? = null

)

