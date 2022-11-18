package com.globits.mita.data.network

import com.globits.mita.data.model.labtest.LabTest
import com.globits.mita.data.model.labtestxray.LabTestXRay
import com.globits.mita.data.model.Page
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.model.prescriptions.PresCripTion
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface TestApi {

//    @POST("patients/search")
//    fun getPatient(@Body patientFilter: PatientFilter): Observable<Page<Patient>>

    @POST("patients/search")
    suspend fun getPatient(@Body patientFilter: PatientFilter): Page<Patient>

    @GET("prescriptions/{id}")
    fun getPrescription(@Path(value = "id") id: Int): Observable<List<PresCripTion>>

    @GET("lab-test-x-rays/{id}")
    fun getLabTestXRay(@Path(value = "id") id: Int): Observable<List<LabTestXRay>>

    @GET("lab-tests/{id}")
    fun getLabTest(@Path(value = "id") id: Int): Observable<List<LabTest>>
}

data class SearchDto(
    val code: String,
    val name: String
)
