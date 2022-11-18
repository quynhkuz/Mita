package com.globits.mita.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.globits.mita.ui.home.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentModule {
    @Binds
    fun bindFragmentFactory(factory: XCareFragmentFactory): FragmentFactory
    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    fun bindHomeFragment(homeFragment: HomeFragment): Fragment

}