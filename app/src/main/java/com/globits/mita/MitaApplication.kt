package com.globits.mita

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.globits.mita.di.DaggerMitaComponent
import com.globits.mita.di.MitaComponent
import com.globits.mita.utils.UpdateReceiver
import timber.log.Timber


open class MitaApplication : Application() {


    val mitaComponent: MitaComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): MitaComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerMitaComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()


        mitaComponent.inject(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(UpdateReceiver(true), filter)

//        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(UpdateReceiver(), intentFilter)
    }



}


