package com.globits.mita.ui.security

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.viewModel
import com.airbnb.mvrx.withState
import com.globits.mita.MitaApplication
import com.globits.mita.R
import com.globits.mita.core.MitaBaseActivity
import com.globits.mita.databinding.ActivityLoginBinding
import com.globits.mita.ui.MainActivity
import javax.inject.Inject

class LoginActivity : MitaBaseActivity<ActivityLoginBinding>(), SecurityViewModel.Factory {
    private val viewModel: SecurityViewModel by viewModel()

    @Inject
    lateinit var securityViewModelFactory: SecurityViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MitaApplication).mitaComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(views.root)

        supportFragmentManager.commit {
            add<SplashFragment>(R.id.frame_layout)
        }
        viewModel.observeViewEvents {
            handleViewEvent(it)
        }
        viewModel.subscribe(this) {
            if (it.isLoading()) {
                views.waitingView.waitingView.visibility = View.VISIBLE
            } else {
                views.waitingView.waitingView.visibility = View.GONE
            }
        }
    }

    private fun handleViewEvent(it: SecurityViewEvent?) {
        when (it) {
            SecurityViewEvent.GetUserEvent -> {
                withState(viewModel) {
                    when (it.asyncUserCurrent) {
                        is Success -> {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        is Fail -> {
                            supportFragmentManager.commit {
                                replace<LoginFragment>(R.id.frame_layout)
                            }
                        }
                        else -> Unit
                    }
                }
            }
            else -> {}
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.handle(SecurityViewAction.GetUserCurrent)
    }

    override fun getBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun create(initialState: SecurityViewState): SecurityViewModel {
        return securityViewModelFactory.create(initialState)
    }

}