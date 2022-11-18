package com.globits.mita.ui.nursing

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.Patient
import com.globits.mita.ui.treatment.view.SetLayoutPatientInfo

class NursingFragmentInfoPatient : MitaBaseFragment() {

    val viewModel: NursingViewModel by activityViewModel()

    var patient = mutableStateOf<Patient>(Patient())

    @Composable
    override fun SetLayout() {

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.primary_color) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE)
        };

        SetLayoutPatientInfo("Thông tin bệnh nhân", true, onBackStack = {
            (activity as NursingActivity).removeBackStack()
        }, onPatientClick = {
            var patientId = it.id!!.toInt()
            (activity as NursingActivity).addFragmentPatient(patientId)
        },
            patient = patient.value
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun invalidate(): Unit = withState(viewModel) {
        it.patient.also {
            if (it != null) {
                patient.value = it
            }
        }

    }

}