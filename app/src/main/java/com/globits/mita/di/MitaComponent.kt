package com.globits.mita.di

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.globits.mita.MitaApplication
import com.globits.mita.ui.MainActivity
import com.globits.mita.ui.assign.AssignActivity
import com.globits.mita.ui.nursing.NursingActivity
import com.globits.mita.ui.pacs.PacsActivity
import com.globits.mita.ui.patients.PatientInfoFragment
import com.globits.mita.ui.security.LoginActivity
import com.globits.mita.ui.treatment.TreatmentActivity

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    ViewModelModule::class,
    FragmentModule::class,
    NetWorkModule::class
])
@Singleton
interface MitaComponent {
    fun inject(xCareApplication: MitaApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(assignActivity: AssignActivity)
    fun inject(pacsActivity: PacsActivity)
    fun inject(patientInfoFragment: PatientInfoFragment)
    fun inject(treatmentActivity: TreatmentActivity)
    fun inject(nursingActivity: NursingActivity)
    fun fragmentFactory(): FragmentFactory
    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MitaComponent
    }
}