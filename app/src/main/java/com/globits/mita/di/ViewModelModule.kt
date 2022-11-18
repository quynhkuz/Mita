package com.globits.mita.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globits.mita.core.MitaBaseViewModel
import com.globits.mita.ui.security.SecurityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule{
    @Binds
    fun bindViewModelFactory(factory: MitaViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SecurityViewModel::class)
    fun bindViewSecurityViewModel(viewModel: MitaBaseViewModel):ViewModel
}