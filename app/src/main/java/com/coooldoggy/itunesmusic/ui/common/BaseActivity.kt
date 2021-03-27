package com.coooldoggy.itunesmusic.ui.common

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.coooldoggy.itunesmusic.R


abstract class BaseActivity : AppCompatActivity(){
    val TAG = BaseActivity::class.java.simpleName

    private var mFragmentId = -1

    private val progressView: View? by lazy {
        val result = runCatching {
            val view: View? = layoutInflater.inflate(R.layout.layout_common_progress, (window.decorView as? ViewGroup), false)
            view?.apply {
                val viewGroup = window.decorView as? ViewGroup
                viewGroup?.addView(this, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            }
        }
        result.getOrNull()
    }

    val viewModelObserver: Observer<BaseViewModelEvent>
        get() = Observer {
            Log.d(TAG, "Observe ViewModel Event : $it")
            when(it){
                is BaseViewModelEvent.Event -> {
                    onViewModelEvent(it.eventId, it.param)
                }
                is BaseViewModelEvent.ShowToastMessage ->{
                    showToastMessage(it.context, it.message)
                }
                is BaseViewModelEvent.ShowProgressBar ->{
                    showProgressBar(it.message)
                }
                is BaseViewModelEvent.HideProgressBar -> {
                    hideProgressBar(it.message)
                }
            }
        }

    abstract fun onViewModelEvent(eventId: Int, param: Any)

    fun showToastMessage(context: Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressBar(message: String?){
        progressView?.visibility = View.VISIBLE
    }

    fun hideProgressBar(message: String?){
        progressView?.visibility = View.GONE
    }

    fun getContext(): Context? = this@BaseActivity.baseContext

    fun observeViewModelEvent(lifecycleOwner: LifecycleOwner, viewModel: BaseViewModel) {
        Log.d(TAG, "observeViewModelEvent $viewModel")
        viewModel.viewModelEvent.observe(lifecycleOwner, viewModelObserver)
    }

    fun removeObserveViewModelEvent(lifecycleOwner: LifecycleOwner, viewModel: BaseViewModel) {
        Log.d(TAG, "removeObserveViewModelEvent $viewModel")
        viewModel.viewModelEvent.removeObservers(lifecycleOwner)
    }

    fun setFragmentID(fragmentId: Int) {
        Log.d(TAG, "setFragmentID : $fragmentId")
        mFragmentId = fragmentId
    }

    fun addFragment(fragment: Fragment) {
        Log.d(TAG, "addFragment")
        addFragment(mFragmentId, fragment)
    }

    fun addFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        Log.d(TAG, "addFragment [$containerViewId, $fragment]")
        if(isFinishing || lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }

        if(fragment.isAdded || fragment.isResumed || fragment.isVisible) {
            return
        }

        runCatching {
            supportFragmentManager.commit(true) {
                add(containerViewId, fragment)
            }
        }
    }

    fun switchFragment(fragment: Fragment) {
        Log.d(TAG, "switchFragment")
        switchFragment(mFragmentId, fragment)
    }

    fun switchFragment(@IdRes containerViewId: Int, fragment: Fragment) {
        Log.d(TAG, "switchFragment [$containerViewId, $fragment]")

        if(isFinishing || lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }

        if(fragment.isAdded || fragment.isResumed || fragment.isVisible) {
            return
        }

        runCatching {
            supportFragmentManager.commit(true) {
                replace(containerViewId, fragment)
            }
        }
    }

    open fun finishFragment(fragment: Fragment) {
        Log.d(TAG, "finishFragment")

        if(isFinishing || lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }

        if(fragment.isRemoving || fragment.isDetached) {
            return
        }

        supportFragmentManager.commit(true) {
            remove(fragment)
        }
    }

    fun finishFragment(fragment: Fragment, @AnimRes animEnter: Int, @AnimRes animExit: Int) {
        Log.d(TAG, "finishFragment")

        if(isFinishing || lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }

        if(fragment.isRemoving || fragment.isDetached) {
            return
        }

        runCatching {
            supportFragmentManager.commit(true) {
                setCustomAnimations(animEnter, animExit, animEnter, animExit)
                remove(fragment)
            }
        }
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        if (fm.fragments.size != 0) {
            val fs = fm.fragments
            val size = fs.size
            for (i in size - 1 downTo 0) {
                val f = fs[i]
                if (f is BaseFragment) {
                    if (!f.onBackPressed()) {
                        if (fm.fragments.size <= 1) {
                            super.onBackPressed()
                            finish()
                            return
                        }
                        f.finishThisFragment()
                    }
                    break
                }
            }
        } else {
            super.onBackPressed()
            finish()
        }
    }
}