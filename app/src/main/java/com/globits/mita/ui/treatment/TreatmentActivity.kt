package com.globits.mita.ui.treatment

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.compose.ui.graphics.Color
import com.globits.mita.MitaApplication
import com.globits.mita.R
import com.globits.mita.core.MitaBaseActivity
import com.globits.mita.databinding.ActivityTreatmentBinding
import com.globits.mita.ui.patients.PatientInfoFragment
import com.globits.mita.utils.addFragment
import com.globits.mita.utils.addFragmentToBackstack
import javax.inject.Inject


class TreatmentActivity : MitaBaseActivity<ActivityTreatmentBinding>(),TreatmentViewModel.Factory {
    @Inject
    lateinit var viewModelFactory: TreatmentViewModel.Factory

    override fun getBinding(): ActivityTreatmentBinding {
        return ActivityTreatmentBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MitaApplication).mitaComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        addFragment(R.id.container,TreatmentFragment::class.java)
    }
    fun addFragmentInfoPatient(){
        addFragmentToBackstack(R.id.container,TreatmentInfoPatientFragment::class.java)
    }
    fun addFragmentPatient(patientId :Int){

        var patientFragment = PatientInfoFragment()
        val args = Bundle()
        args.putInt("patientId", patientId)
        patientFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(R.id.container,patientFragment).addToBackStack(null).commit()

    }
    fun removeBackStack(){
        supportFragmentManager.popBackStack()
    }

    override fun create(initialState: TreatmentViewState): TreatmentViewModel {
        return viewModelFactory.create(initialState)
    }

//    fun updateStatusBarColor(color: String?) { // Color must be in hexadecimal fromat
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window: Window = window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.setStatusBarColor(Color.parseColor(color))
//        }
//    }

}
