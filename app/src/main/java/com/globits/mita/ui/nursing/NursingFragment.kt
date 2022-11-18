package com.globits.mita.ui.nursing

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
import com.globits.mita.ui.nursing.view.SetLayoutListPatientFragment
import com.globits.mita.utils.snackbar


class NursingFragment : MitaBaseFragment() {


    val viewModel: NursingViewModel by activityViewModel()

    var valueState = mutableStateOf("Đang điều trị")

    var _listUser = mutableStateOf<List<Patient>>(mutableListOf())
    private val listUser: State<List<Patient>> = _listUser


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

        SetLayoutListPatientFragment(
            onClickListener = {
                (activity as NursingActivity).addFragmentInfoPatient()
                viewModel.handle(NursingViewAction.SetPatientDetail(it))
            },
            onBackStack = { (activity as NursingActivity).finish() },
            getPatient = {
                viewModel.handle(
                    NursingViewAction.GetPatients(it)
                )
                valueState.value = if (it == 0) "Xem tất cả" else "Đang điều trị"
            },
            listPatient,
            valueState
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun invalidate() = withState(viewModel) {
        updateState(it)
    }

    private fun updateState(it: NursingViewState) {
        when (it.asyncPatients) {
            is Success -> {
                it.asyncPatients.invoke()?.let {
                    if (it != null) {
                        //_listUser.value=it
                        _listUser.value = it.content!!
                    }
                }
            }
            is Fail -> {
                requireActivity().snackbar("Đã xảy ra lỗi xin vui lòng thử lại.")
            }
            else -> {}

        }
    }

}