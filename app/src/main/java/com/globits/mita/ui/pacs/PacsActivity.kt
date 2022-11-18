package com.globits.mita.ui.pacs

import android.os.Bundle
import android.widget.SeekBar
import androidx.compose.material.Snackbar
import com.globits.mita.MitaApplication
import com.globits.mita.R
import com.globits.mita.core.MitaBaseActivity
import com.globits.mita.databinding.ActivityPacsBinding
import com.globits.mita.ui.assign.AssignViewModel
import com.globits.mita.ui.assign.AssignViewState
import com.globits.mita.utils.addFragment
import com.globits.mita.utils.addFragmentToBackstack
import com.globits.mita.utils.snackbar
import javax.inject.Inject

class PacsActivity : MitaBaseActivity<ActivityPacsBinding>(),PacsViewModel.Factory {

    @Inject
    lateinit var viewModelFactory: PacsViewModel.Factory

    override fun getBinding(): ActivityPacsBinding {
        (applicationContext as MitaApplication).mitaComponent.inject(this)
        return ActivityPacsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        addFragment(R.id.container, PacsFragment::class.java)
    }

    fun addFragmentInfoPatient() {
        addFragmentToBackstack(R.id.container, PacsInfoFragment::class.java)
    }

    fun addFragmentImage() {
        addFragmentToBackstack(R.id.container, PacsImageFragment::class.java)
    }


    fun removeBackStack(){
        supportFragmentManager.popBackStack()
    }

    fun showSnackbar()
    {
       snackbar("Không có dữ liệu hiển thị")
    }

    override fun create(initialState: PacsViewState): PacsViewModel {
        return viewModelFactory.create(initialState)
    }


}