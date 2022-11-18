package com.globits.mita.ui.treatment

import android.os.Build
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.ui.nursing.view.SetLayoutListPatientFragment
import com.globits.mita.utils.snackbar


class TreatmentFragment : MitaBaseFragment() {


    val viewModel: TreatmentViewModel by activityViewModel()

    var valueState = mutableStateOf("Đang điều trị")

    @Composable
    override fun SetLayout() {

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

                    val e = loadState.refresh as LoadState.NotLoading
                    if (!e.endOfPaginationReached) {
                        activity?.snackbar("Có lỗi xảy ra")
                    }
                }
            }

        }

        SetLayoutListPatientFragment(onClickListener = {
            (activity as TreatmentActivity).addFragmentInfoPatient()
            viewModel.handle(TreatmentViewAction.SetPatientDetail(it))
        }, onBackStack = { (activity as TreatmentActivity).finish() },
            getPatient = {
                viewModel.handle(
                    TreatmentViewAction.GetPatients(it)
                )
                valueState.value = if (it == 0) "Xem tất cả" else "Đang điều trị"
            },
            listPatient,
            valueState
        )
    }


    override fun invalidate() = withState(viewModel) {
    }


}