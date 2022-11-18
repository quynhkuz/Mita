package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class Department(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("departmentType")
    val departmentType: Int? = null,
    @SerializedName("name")
    val name :String?=null,
    @SerializedName("parent")
    val parent :Department?=null,
    @SerializedName("isAssetManagement")
    val isAssetManagement :Boolean?=null,

    @SerializedName("duplicate")
    val duplicate :Boolean?=null,

    @SerializedName("viewIndex")
    val viewIndex :String?=null,

    )
{
    override fun toString():String
    {
        return name!!
    }
}
data class DepartmentFilter(
    @SerializedName("keyword")
    val keyword: String? = null,
    @SerializedName("pageIndex")
    val pageIndex: Int? = null,
    @SerializedName("pageSize")
    val pageSize: Int? = null,
)
data class DepartmentReceiver(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("text")
    val text: String? = null,
){
    override fun toString():String
    {
        return text!!
    }
}

