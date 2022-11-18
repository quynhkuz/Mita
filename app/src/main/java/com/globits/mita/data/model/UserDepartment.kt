package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class UserDepartment(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("name")
    val name: String? = null,
)

data class UserFilter(
    @SerializedName("departmentId")
    val departmentId: String? = null,
    @SerializedName("pageIndex")
    val pageIndex: Int? = null,
    @SerializedName("pageSize")
    val pageSize: Int? = null,
    @SerializedName("isAssetManagement")
    val isAssetManagement: Boolean? = null,
)
