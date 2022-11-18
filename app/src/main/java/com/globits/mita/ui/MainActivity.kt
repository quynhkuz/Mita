package com.globits.mita.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.viewModel
import com.globits.mita.R

import com.globits.mita.MitaApplication
import com.globits.mita.core.MitaBaseActivity
import com.globits.mita.databinding.ActivityMainBinding
import com.globits.mita.ui.home.HomeViewModel
import com.globits.mita.ui.home.HomeViewState
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


class MainActivity : MitaBaseActivity<ActivityMainBinding>(), HomeViewModel.Factory {

    val viewModel: HomeViewModel by viewModel()

    @Inject
    lateinit var viewModelFactory: HomeViewModel.Factory


    override fun onCreate(savedInstanceState: Bundle?) {


        (applicationContext as MitaApplication).mitaComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(views.root)

        val navView: BottomNavigationView = views.navView

        var navController = findNavController(R.id.navigate)

        var appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_note,
                R.id.nav_notification,
                R.id.nav_setting
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel.subscribe(this){
            if(it.isLoading())
            {
                views.waitingView.waitingView.visibility= View.VISIBLE
            }
            else{
                views.waitingView.waitingView.visibility= View.GONE
            }
        }
    }


    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun create(initialState: HomeViewState): HomeViewModel {
        return viewModelFactory.create(initialState)
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()

    }


}

