package com.globits.mita.data.repository

import com.globits.mita.data.model.Page
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.data.model.labtestassign.LabTestAssign
import com.globits.mita.data.model.labtestassign.LabTestAssignTemplate
import com.globits.mita.data.network.LabTestApi
import com.globits.mita.data.network.TestApi
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabTestRepository @Inject constructor(
    val api: LabTestApi
) {
    fun getLabTestAssign(patientId: Long): Observable<List<LabTestAssign>> =
        api.getLabTestAssign(patientId).subscribeOn(Schedulers.io())

    fun getLabTestAssignTemplate(): Observable<List<LabTestAssignTemplate>> =
        api.getLabTestAssignTemplate().subscribeOn(Schedulers.io())

    fun saveLabTestAssign(patientId: Long, listLabTestAssign: List<LabTestAssign>): Observable<List<LabTestAssign>> =
        api.saveLabTestAssign(patientId,listLabTestAssign).subscribeOn(Schedulers.io())
}