package com.globits.mita.ui.assign

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.Patient
import com.globits.mita.data.model.PatientFilter
import com.globits.mita.ui.assign.view.SetLayoutListPatientFragmentAssign
import com.globits.mita.utils.snackbar


class AssignFragment : MitaBaseFragment() {

    val viewModel: AssignViewModel by activityViewModel()

    var valueState = mutableStateOf("Đang điều trị")



    @Composable
    override fun SetLayout() {

        val listPatient = viewModel.getPatient.collectAsLazyPagingItems()

        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.white) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        };

        listPatient.apply {
            when {
                /** Showing error state */
                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    if (e.error is Exception) {
                        activity?.snackbar("Có lỗi xảy ra")
                    }
                }

                /** Showing error state */
                loadState.append is LoadState.Error -> {

                    val e = loadState.refresh as LoadState.Error
                    if (e.error is Exception) {
                        activity?.snackbar("Có lỗi xảy ra")
                    }
                }
            }

        }

        SetLayoutListPatientFragmentAssign(onClickListener = {
            viewModel.handle(AssignViewAction.SetPatientDetail(it))
            (activity as AssignActivity).addFragmentInfoPatient()

        }, onBackStack = {
            (activity as AssignActivity).finish()
        }, listUser = listPatient,
            getPatient = {
                viewModel.handle(AssignViewAction.GetPatients(it))
                valueState.value = if (it == 0) "Xem tất cả" else "Đang điều trị"
            },
            valueState = valueState
        )


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun invalidate() = withState(viewModel) {

    }


}