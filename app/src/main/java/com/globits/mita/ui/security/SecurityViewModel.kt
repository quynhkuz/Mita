package com.globits.mita.ui.security
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.globits.mita.core.MitaViewModel
import com.globits.mita.data.model.TokenResponse
import com.globits.mita.data.repository.AuthRepository
import com.globits.mita.data.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async


class SecurityViewModel @AssistedInject constructor(
    @Assisted state: SecurityViewState,
    val repository: AuthRepository,
    val userRepo: UserRepository
) :
    MitaViewModel<SecurityViewState, SecurityViewAction, SecurityViewEvent>(state) {
    init {

    }

    override fun handle(action: SecurityViewAction) {
        when(action){
            is SecurityViewAction.LogginAction->handleLogin(action.userName,action.password)
            is SecurityViewAction.SaveTokenAction->handleSaveToken(action.token)
            is SecurityViewAction.GetUserCurrent ->handleCurrentUser()
        }
    }

    private fun handleCurrentUser() {
        setState { copy(asyncUserCurrent=Loading()) }
        userRepo.getCurrentUser().execute {
            _viewEvents.post(SecurityViewEvent.GetUserEvent)
            copy(asyncUserCurrent=it)
        }
    }

    private fun handleLogin(userName:String,password: String){
        setState {
            copy(asyncLogin=Loading())
        }
        repository.login(userName,password).execute {
            copy(asyncLogin=it)
        }
    }
    private fun handleSaveToken(tokenResponse: TokenResponse)
    {
        this.viewModelScope.async {
            repository.saveAccessTokens(tokenResponse)
        }

    }

    @AssistedFactory
    interface Factory {
        fun create(initialState: SecurityViewState): SecurityViewModel
    }

    companion object : MvRxViewModelFactory<SecurityViewModel, SecurityViewState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: SecurityViewState): SecurityViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }

            return factory?.create(state) ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}