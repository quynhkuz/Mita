package com.globits.mita.data.repository

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.labtest.LabTest
import com.globits.mita.data.model.labtestxray.LabTestXRay
import com.globits.mita.data.model.prescriptions.PresCripTion
import com.globits.mita.data.network.TestApi
import com.globits.mita.data.paging.GetPatientSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepository @Inject constructor(
    val api: TestApi
) {

//    fun getPatient(patientFilter: PatientFilter): Observable<Page<Patient>> =
//        api.getPatient(patientFilter).subscribeOn(Schedulers.io())

    fun getPatient(status: Int): Flow<PagingData<Patient>> {
        return Pager(
            config = PagingConfig(
                pageSize =4
            ),
            pagingSourceFactory = {
                GetPatientSource(api, status)
            }
        ).flow
    }


    fun getPrescription(patientId: Int): Observable<List<PresCripTion>> =
        api.getPrescription(patientId).subscribeOn(Schedulers.io())

    fun getLabTestXRay(patientId: Int): Observable<List<LabTestXRay>> =
        api.getLabTestXRay(patientId).subscribeOn(Schedulers.io())

    fun getLabTest(patientId: Int): Observable<List<LabTest>> =
        api.getLabTest(patientId).subscribeOn(Schedulers.io())
}