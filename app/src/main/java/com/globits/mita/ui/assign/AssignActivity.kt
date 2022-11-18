package com.globits.mita.ui.assign

import android.os.Bundle
import com.airbnb.mvrx.viewModel
import com.globits.mita.MitaApplication
import com.globits.mita.R
import com.globits.mita.core.MitaBaseActivity
import com.globits.mita.databinding.ActivityAssignBinding
import com.globits.mita.utils.addFragment
import com.globits.mita.utils.addFragmentToBackstack
import javax.inject.Inject

class AssignActivity : MitaBaseActivity<ActivityAssignBinding>(), AssignViewModel.Factory {

    @Inject
    lateinit var viewModelFactory: AssignViewModel.Factory

    val viewModel: AssignViewModel by viewModel()

    override fun getBinding(): ActivityAssignBinding {
        return ActivityAssignBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MitaApplication).mitaComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        addFragment(R.id.container, AssignFragment::class.java, null)
    }

    fun addFragmentInfoPatient() {
        addFragmentToBackstack(R.id.container, AssignInfoFragment::class.java, null)
    }

    fun removeBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun create(initialState: AssignViewState): AssignViewModel {
        return viewModelFactory.create(initialState)
    }

}