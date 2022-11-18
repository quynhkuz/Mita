package com.globits.mita.ui.nursing

import android.os.Bundle
import com.globits.mita.MitaApplication
import com.globits.mita.R
import com.globits.mita.core.MitaBaseActivity
import com.globits.mita.databinding.ActivityNursingBinding
import com.globits.mita.ui.pacs.PacsViewModel
import com.globits.mita.ui.patients.PatientInfoFragment
import com.globits.mita.utils.addFragment
import com.globits.mita.utils.addFragmentToBackstack
import javax.inject.Inject

class NursingActivity : MitaBaseActivity<ActivityNursingBinding>(),NursingViewModel.Factory {


    @Inject
    lateinit var viewModelFactory: NursingViewModel.Factory

    override fun getBinding(): ActivityNursingBinding {
        (applicationContext as MitaApplication).mitaComponent.inject(this)
        return ActivityNursingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        addFragment(R.id.container, NursingFragment::class.java, null)
    }

    fun addFragmentInfoPatient() {
        addFragmentToBackstack(R.id.container, NursingFragmentInfoPatient::class.java, null)
    }

    fun addFragmentPatient(patientId :Int) {

        var patientFragment = PatientInfoFragment()
        val args = Bundle()
        args.putInt("patientId", patientId)
        patientFragment.arguments = args
        supportFragmentManager.beginTransaction().replace(R.id.container,patientFragment).addToBackStack(null).commit()
    }

    fun removeBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun create(initialState: NursingViewState): NursingViewModel {
        return viewModelFactory.create(initialState)
    }


}