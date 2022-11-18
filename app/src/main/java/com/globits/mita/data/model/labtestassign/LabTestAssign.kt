package com.globits.mita.data.model.labtestassign

import com.globits.mita.data.model.Patient

data class LabTestAssign(
    val id: Long? = null,
    val patient: Patient? = null,
    val labTestAssignTemplate: LabTestAssignTemplate? = null

)
