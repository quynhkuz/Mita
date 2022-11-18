package com.globits.mita.data.network

import com.globits.mita.data.model.Page
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.model.Responsive
import com.globits.mita.data.model.labtestassign.LabTestAssign
import com.globits.mita.data.model.labtestassign.LabTestAssignTemplate
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface LabTestApi {

    @GET("lab-test-assigns/{id}")
    fun getLabTestAssign(@Path(value = "id") patientId: Long): Observable<List<LabTestAssign>>

    @GET("lab-test-assigns/templates")
    fun getLabTestAssignTemplate(): Observable<List<LabTestAssignTemplate>>

    @POST("lab-test-assigns/{id}")
    fun saveLabTestAssign(@Path(value = "id") patientId: Long, @Body listLabTestAssign : List<LabTestAssign>): Observable<List<LabTestAssign>>
}

