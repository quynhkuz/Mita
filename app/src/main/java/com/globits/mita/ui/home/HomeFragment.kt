package com.globits.mita.ui.home


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import com.globits.mita.R
import com.globits.mita.core.MitaBaseFragment
import com.globits.mita.data.model.User
import com.globits.mita.ui.MainActivity
import com.globits.mita.ui.assign.AssignActivity
import com.globits.mita.ui.nursing.NursingActivity
import com.globits.mita.ui.pacs.PacsActivity
import com.globits.mita.ui.treatment.TreatmentActivity
import java.util.*
import javax.inject.Inject

class HomeFragment @Inject constructor() : MitaBaseFragment() {
    private val viewModel: HomeViewModel by activityViewModel()

    var user = mutableStateOf<User>(User())

    @Composable
    override fun SetLayout() {


        setLayoutFragment(
            requireContext(),
            onClickListNursing = {
                (activity as MainActivity).startActivity(
                    Intent(
                        requireContext(),
                        NursingActivity::class.java
                    )
                )
            },
            onClickListTreatment = {
                (activity as MainActivity).startActivity(
                    Intent(
                        requireContext(),
                        TreatmentActivity::class.java
                    )
                )
            },
            onClickListPacs = {
                (activity as MainActivity).startActivity(
                    Intent(
                        requireContext(),
                        PacsActivity::class.java
                    )
                )
            },
            onClickListAssign = {
                (activity as MainActivity).startActivity(
                    Intent(
                        requireContext(),
                        AssignActivity::class.java
                    )
                )
            },
            user
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeViewEvents {
            handleEvent(it)
        }
        viewModel.handle(HomeViewAction.GetAnalytics)
        setListener()
    }

    private fun handleEvent(event: HomeViewEvent) {
        when {

        }
    }

    private fun setListener() {

    }

    override fun invalidate() = withState(viewModel) {
        when(it.userCurrent)
        {
            is Success ->{
                it.userCurrent.invoke()?.let { token ->
                    user.value = token
                }
            }
            is Fail ->
            {
                Toast.makeText(requireContext(),"lỗi", Toast.LENGTH_LONG).show()
            }
            else->{}
        }
    }

}

data class Daily(
    val time: Calendar,
    val doctorName: String,
    val depcription: String,
)