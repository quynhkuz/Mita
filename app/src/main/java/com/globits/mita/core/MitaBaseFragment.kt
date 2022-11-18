/*
 * Copyright 2019 New qlts Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("DEPRECATION")

package com.globits.mita.core

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.BaseMvRxFragment
import com.globits.mita.di.DaggerMitaComponent
import com.globits.mita.di.HasScreenInjector
import com.globits.mita.di.MitaComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

abstract class MitaBaseFragment : BaseMvRxFragment(), HasScreenInjector {

    protected val mitaBaseActivity: MitaBaseActivity<*> by lazy {
        activity as MitaBaseActivity<*>
    }

    /* ==========================================================================================
     * Navigator and other common objects
     * ========================================================================================== */

    private lateinit var screenComponent: MitaComponent

    private var progress: ProgressDialog? = null

    /* ==========================================================================================
     * View model
     * ========================================================================================== */

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val activityViewModelProvider
        get() = ViewModelProvider(requireActivity(), viewModelFactory)

    protected val fragmentViewModelProvider
        get() = ViewModelProvider(this, viewModelFactory)

    /* ==========================================================================================
     * Life cycle
     * ========================================================================================== */

    override fun onAttach(context: Context) {
        screenComponent = DaggerMitaComponent.factory().create(context)
        childFragmentManager.fragmentFactory = screenComponent.fragmentFactory()
        super.onAttach(context)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getMenuRes() != -1) {
            setHasOptionsMenu(true)
        }
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("onCreateView Fragment ${javaClass.simpleName}")

        return ComposeView(requireContext()).apply {
            setContent {
                SetLayout()
            }
        }


    }

    @Composable
    abstract fun SetLayout()

    //abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    @CallSuper
    override fun onResume() {
        super.onResume()
        Timber.i("onResume Fragment ${javaClass.simpleName}")
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        Timber.i("onPause Fragment ${javaClass.simpleName}")
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated Fragment ${javaClass.simpleName}")
    }
//
//    open fun showLoading(message: CharSequence?) {
//        showLoadingDialog(message)
//    }
//
//    open fun showFailure(throwable: Throwable) {
//        displayErrorDialog(throwable)
//    }

    @CallSuper
    override fun onDestroyView() {
        Timber.i("onDestroyView Fragment ${javaClass.simpleName}")
        uiDisposables.clear()
        super.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        Timber.i("onDestroy Fragment ${javaClass.simpleName}")
        uiDisposables.dispose()
        super.onDestroy()
    }

    override fun injector(): MitaComponent {
        return screenComponent
    }

    /* ==========================================================================================
     * Restorable
     * ========================================================================================== */

//    private val restorables = ArrayList<Restorable>()

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        restorables.forEach { it.onSaveInstanceState(outState) }
//        restorables.clear()
//    }

//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        restorables.forEach { it.onRestoreInstanceState(savedInstanceState) }
//        super.onViewStateRestored(savedInstanceState)
//    }

    override fun invalidate() {
        // no-ops by default
        Timber.v("invalidate() method has not been implemented")
    }

//    protected fun setArguments(args: Parcelable? = null) {
//        arguments = args.toMvRxBundle()
//    }
//
//    @MainThread
//    protected fun <T : Restorable> T.register(): T {
//        assertMainThread()
//        restorables.add(this)
//        return this
//    }

//    protected fun showErrorInSnackbar(throwable: Throwable) {
//        qltsBaseActivity.getCoordinatorLayout()?.showOptimizedSnackbar(errorFormatter.toHumanReadable(throwable))
//    }

//    protected fun showLoadingDialog(message: CharSequence? = null, cancelable: Boolean = false) {
//        progress?.dismiss()
//        progress = ProgressDialog(requireContext()).apply {
//            setCancelable(cancelable)
//            setMessage(message ?: getString(R.string.please_wait))
//            setProgressStyle(ProgressDialog.STYLE_SPINNER)
//            show()
//        }
//    }

    protected fun dismissLoadingDialog() {
        progress?.dismiss()
    }

    /* ==========================================================================================
     * Toolbar
     * ========================================================================================== */

    /**
     * Configure the Toolbar.
     */
//    protected fun setupToolbar(toolbar: Toolbar) {
//        val parentActivity = qltsBaseActivity
//        if (parentActivity is ToolbarConfigurable) {
//            parentActivity.configure(toolbar)
//        }
//    }

    /* ==========================================================================================
     * Disposable
     * ========================================================================================== */

    private val uiDisposables = CompositeDisposable()

    protected fun Disposable.disposeOnDestroyView() {
        uiDisposables.add(this)
    }

    /* ==========================================================================================
     * ViewEvents
     * ========================================================================================== */

    protected fun <T : MitaViewEvents> MitaViewModel<*, *, T>.observeViewEvents(observer: (T) -> Unit) {
        viewEvents
            .observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                dismissLoadingDialog()
                observer(it)
            }
            .disposeOnDestroyView()
    }

    /* ==========================================================================================
     * Views
     * ========================================================================================== */

//    protected fun View.debouncedClicks(onClicked: () -> Unit) {
//        clicks()
//                .throttleFirst(300, TimeUnit.MILLISECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { onClicked() }
//                .disposeOnDestroyView()
//    }

    /* ==========================================================================================
     * MENU MANAGEMENT
     * ========================================================================================== */

    open fun getMenuRes() = -1

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuRes = getMenuRes()

        if (menuRes != -1) {
            inflater.inflate(menuRes, menu)
        }
    }

    // This should be provided by the framework
    protected fun invalidateOptionsMenu() = requireActivity().invalidateOptionsMenu()

    /* ==========================================================================================
     * Common Dialogs
     * ========================================================================================== */

//    protected fun displayErrorDialog(throwable: Throwable) {
//        AlertDialog.Builder(requireActivity())
//                .setTitle(R.string.dialog_title_error)
//                .setMessage(errorFormatter.toHumanReadable(throwable))
//                .setPositiveButton(R.string.ok, null)
//                .show()
//    }
}
