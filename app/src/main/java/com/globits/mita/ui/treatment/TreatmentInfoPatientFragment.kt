package com.globits.mita.ui.treatment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.Patient
import com.globits.mita.ui.treatment.view.SetLayoutPatientInfo


class TreatmentInfoPatientFragment : MitaBaseFragment() {

    val viewModel: TreatmentViewModel by activityViewModel()

    var patient = mutableStateOf<Patient>(Patient())



    @Composable
    override fun SetLayout() {

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.primary_color) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE)
        };

        SetLayoutPatientInfo("Thông tin bệnh nhân",
            false,
            onBackStack = { (activity as TreatmentActivity).removeBackStack() },
            onPatientClick = {
               var patientId = it.id!!.toInt()
                (activity as TreatmentActivity).addFragmentPatient(patientId)
                             },
        patient = patient.value)
    }


    override fun invalidate(): Unit = withState(viewModel) {
        it.patient.also {
            if (it != null) {
                patient.value = it
            }
        }

    }

}