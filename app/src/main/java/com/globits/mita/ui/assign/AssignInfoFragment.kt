package com.globits.mita.ui.assign

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.labtestassign.LabTestAssign
import com.globits.mita.data.model.labtestassign.LabTestAssignTemplate
import com.globits.mita.ui.assign.AssignViewEvent.GetLabTestAssignEvent
import com.globits.mita.ui.assign.view.CustomDialogScrollable
import com.globits.mita.ui.assign.view.ProgressDialog
import com.globits.mita.ui.assign.view.SetLayoutPatientInfoAssign
import com.globits.mita.utils.snackbar


class AssignInfoFragment : MitaBaseFragment() {

    val viewModel: AssignViewModel by activityViewModel()

    var patient = mutableStateOf<Patient>(Patient())
    var isGetLabTestAssign: Boolean = true
    var listLabTest = mutableStateListOf<LabTestAssign>()
    var listLabTestTemplate: MutableState<List<LabTestAssignTemplate>> =
        mutableStateOf(mutableListOf())

    var listLabTestTemplateFilter = mutableStateListOf<LabTestAssignTemplate>()

    var openProgressDialog =  mutableStateOf(false)


    @Composable
    override fun SetLayout() {

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.primary_color) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.ACCESSIBILITY_LIVE_REGION_ASSERTIVE)
        };

        var openDialog by remember { mutableStateOf(false) }

        if (openDialog) {

            //loc listlabTestAssignTemplate
            var ischeck = true
            listLabTestTemplateFilter.clear()
           for (itemTemplate in listLabTestTemplate.value)
           {
               for (it in listLabTest)
               {
                   ischeck = true
                   if (itemTemplate.name.equals(it.labTestAssignTemplate?.name)) {
                      ischeck = false
                       break
                   }
               }
               if (ischeck)
               {
                   listLabTestTemplateFilter.add(itemTemplate)
               }
           }
            //show dialog
            CustomDialogScrollable(
                onConfirmClicked = {
                    it.forEach { item ->
                        listLabTest.add(
                            LabTestAssign(
                                patient = patient.value,
                                labTestAssignTemplate = item
                            )
                        )
                    }
                    openDialog = !openDialog
                },
                onDismiss = { openDialog = !openDialog },
                listLabTestTemplateFilter
            )
        }

        //show progessdialog
        AnimatedVisibility(
            visible = openProgressDialog.value,
            enter = fadeIn(
                // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                initialAlpha = 0.4f
            ),
            exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            ProgressDialog()
        }




        SetLayoutPatientInfoAssign(onBackStack = {
            (activity as AssignActivity).removeBackStack()
        },
            patient.value,
            listLabTest,
            onClickInsert = {
                viewModel.handle(AssignViewAction.GetLabTestAssignTemplate)
                openDialog = true
            },
            onClickRemove = { listLabTest.remove(it) },
            onClickSave = {
                if (patient.value !=null)
                {
                    if (patient.value.id != null)
                    {
                        viewModel.handle(AssignViewAction.SaveLabTestAssign(patient.value.id!!,listLabTest))
                    }
                }

            }
        )
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeViewEvents {
            handleViewEvent(it)
        }

        viewModel.subscribe(this) {
            openProgressDialog.value = it.isLoading()
        }
    }







    private fun handleViewEvent(it: AssignViewEvent?) {
        when (it) {
            GetLabTestAssignEvent -> {
                withState(viewModel) {
                    when (it.asyncLabTestAssign) {
                        is Success -> {
                            it.asyncLabTestAssign.invoke().let {
                                listLabTest.clear()
                                listLabTest.addAll(it)
                            }
                        }
                        else -> {
                        }
                    }
                }
            }

            AssignViewEvent.SaveLabTestAssignEvent -> {
                withState(viewModel) {
                    when (it.asyncSaveLabTestAssign) {
                        is Success -> {
                            it.asyncSaveLabTestAssign.invoke().let {
                                listLabTest.clear()
                                listLabTest.addAll(it)
                                requireActivity().snackbar("Lưu thành công.")
                            }
                        }
                        is Fail -> {
                            requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
                        }
                        else ->{}

                    }
                }
            }

            else -> {}
        }
    }


    override fun invalidate(): Unit = withState(viewModel) {

        it.patient.also { it ->
            if (it != null) {
                patient.value = it
                if (isGetLabTestAssign) {
                    it.id?.let {
                        viewModel.handle(AssignViewAction.GetLabTestAssign(it))
                    }
                    isGetLabTestAssign = false
                }
            }
        }


        when (it.asyncLabTestAssignTemplate) {
            is Success -> {
                it.asyncLabTestAssignTemplate.invoke()?.let {
                    if (it != null) {
                        listLabTestTemplate.value = it
                    }
                }
            }
            is Fail -> {
                requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
            }
            else -> {}
        }



//        when (it.asyncSaveLabTestAssign) {
//            is Success -> {
//                it.asyncSaveLabTestAssign.invoke()?.let {
//                    if (it != null) {
//                        listLabTest.clear()
//                        listLabTest.addAll(it)
//                    }
//                }
//            }
//            is Fail -> {
//                requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
//            }
//            else -> {}
//        }

    }

}

