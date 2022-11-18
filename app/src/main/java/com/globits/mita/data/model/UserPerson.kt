package com.globits.mita.data.model

import com.google.gson.annotations.SerializedName

data class UserPerson(
    @SerializedName("displayName")
    val displayName:String?=null,
    @SerializedName("email")
    val email:String?=null,
    @SerializedName("firstName")
    val firstName:String?=null,
    @SerializedName("gender")
    val gender:String?=null,
    @SerializedName("id")
    val id:String?=null,
    @SerializedName("lastName")
    val lastName:String?=null,
    @SerializedName("phoneNumber")
    val phoneNumber:String?=null,
    @SerializedName("userId")
    val userId:String?=null,
    @SerializedName("departmentName")
    var departmentName:String?=null,

    )
{
    override fun toString(): String {
        return displayName.toString()
    }
}

