package com.xsmile2008.righttests.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.xsmile2008.righttests.application.AppClass
import com.xsmile2008.righttests.dialogs.LoadingDialog
import com.xsmile2008.righttests.livedata.ViewAction
import com.xsmile2008.righttests.viewmodels.BaseViewModel
import com.xsmile2008.righttests.viewmodels.ViewModelFactory
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppClass.component.inject(this)

        viewModel.viewAction.observe(this, Observer {
            if (!onViewAction(it!!)) {
                handleViewActionDefault(it)
            }
        })
        viewModel.showSpinner.observe(this, Observer {
            changeSpinnerDialogState(it == true)
        })
    }

    /**
     * Called when view model posts [ViewAction].
     *
     * @return true if action was handled and default handling
     * implementation does not need to be called, false otherwise
     */
    open fun onViewAction(action: ViewAction): Boolean = false

    private fun handleViewActionDefault(action: ViewAction) {
        when (action) {
            is ViewAction.Navigate -> navigate(action)
            is ViewAction.Finish -> doFinish(action)
        }
    }

    private fun navigate(action: ViewAction.Navigate) {
        val intent = action.buildIntent(this)
        if (action.requestCode == null) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, action.requestCode)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun changeSpinnerDialogState(isShouldShow: Boolean) {
        if (isShouldShow) {
            LoadingDialog.newInstance().show(supportFragmentManager, LoadingDialog::class.java.name)
        } else {
            (supportFragmentManager.findFragmentByTag(LoadingDialog::class.java.name) as? DialogFragment)?.dismiss()
        }
    }

    private fun doFinish(action: ViewAction.Finish) {
        action.resultCode?.let { setResult(it) }
        finish()
    }

    protected inline fun <reified T : BaseViewModel> viewModelDelegate() =
            object : ReadOnlyProperty<BaseActivity, T> {

                var value: T? = null

                override fun getValue(thisRef: BaseActivity, property: KProperty<*>): T {
                    if (value == null) {
                        value = ViewModelProviders.of(thisRef, viewModelFactory).get(T::class.java)
                    }
                    return value as T
                }
            }
}