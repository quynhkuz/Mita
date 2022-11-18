package com.globits.mita.ui.patients

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.airbnb.mvrx.*
import com.globits.mita.MitaApplication
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.labtest.LabTest
import com.globits.mita.data.model.labtestxray.LabTestXRay
import com.globits.mita.data.model.prescriptions.PresCripTion
import com.globits.mita.ui.nursing.NursingActivity
import com.globits.mita.ui.treatment.TreatmentActivity
import com.globits.mita.utils.snackbar
import javax.inject.Inject


class PatientInfoFragment : MitaBaseFragment(),PatientViewModel.Factory {

    @Inject
    lateinit var viewModelFactory: PatientViewModel.Factory

    val viewModel: PatientViewModel by fragmentViewModel()

    var listLabTest = mutableStateOf<List<LabTest>>(mutableListOf())
    var listLabTestXRay = mutableStateOf<List<LabTestXRay>>(mutableListOf())
    var listPresCripTion = mutableStateOf<List<PresCripTion>>(mutableListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MitaApplication).mitaComponent.inject(this)

        val args = arguments
        var patientId = args!!.getInt("patientId", 0)

        viewModel.handle(PatientViewAction.GetLabTest(patientId))
        viewModel.handle(PatientViewAction.GetLabTestXRay(patientId))
        viewModel.handle(PatientViewAction.GetPrescription(patientId))



    }


    @Composable
    override fun SetLayout() {


        SetLayoutPatientActivity(
            listLabTest = listLabTest.value,
            listLabTestXRay = listLabTestXRay.value,
            listPrescription = listPresCripTion.value
        ) {
            if (activity is NursingActivity) {
                (activity as NursingActivity).removeBackStack()
            } else (activity as TreatmentActivity).removeBackStack()
        }



    }




    override fun invalidate() = withState(viewModel) {
        updateState(it)
    }


    private fun updateState(it: PatientViewState) {
        when (it.asyncLabTest) {
            is Success -> {
                it.asyncLabTest.invoke()?.let {
                    if (it != null) {
                        listLabTest.value = it
                    }
                }
            }
            is Fail -> {
                requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
            }
            else -> {}
        }

        when (it.asyncLabTestXRay) {
            is Success -> {
                it.asyncLabTestXRay.invoke()?.let {
                    if (it != null) {
                        listLabTestXRay.value = it
                    }
                }
            }
            is Fail -> {
                requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
            }
            else -> {}
        }

        when (it.asyncPresentation) {
            is Success -> {
                it.asyncPresentation.invoke()?.let {
                    if (it != null) {
                        listPresCripTion.value = it
                    }
                }
            }
            is Fail -> {
                requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
            }
            else -> {}
        }
    }


    override fun create(initialState: PatientViewState): PatientViewModel {
        return viewModelFactory.create(initialState)
    }

}