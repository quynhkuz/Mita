package com.globits.mita.ui.pacs

import android.os.Build
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
import javax.inject.Inject


class PacsFragment @Inject constructor() : MitaBaseFragment() {

    val viewModel: PacsViewModel by activityViewModel()

    var valueState = mutableStateOf("Đang điều trị")


    @Composable
    override fun SetLayout() {
        //setStatusBarColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity()?.getWindow()?.setStatusBarColor(getActivity()?.getColor(R.color.white) ?: R.color.white)
            getActivity()?.getWindow()?.getDecorView()?.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        };

        val listPatient = viewModel.getPatient.collectAsLazyPagingItems()

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

        SetLayoutListPatientFragmentAssign(onBackStack = {
            (activity as PacsActivity).finish()
        }, onClickListener = {
            viewModel.handle(PacsViewAction.SetPatientDetail(it))
            (activity as PacsActivity).addFragmentInfoPatient()
        }, listUser = listPatient, getPatient = {
            viewModel.handle(PacsViewAction.GetPatients(it))
            valueState.value = if (it == 0) "Xem tất cả" else "Đang điều trị"
        },
            valueState = valueState
        )
    }

    override fun invalidate() = withState(viewModel) {

    }


}