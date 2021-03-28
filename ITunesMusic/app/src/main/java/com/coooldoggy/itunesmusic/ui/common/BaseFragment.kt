package com.coooldoggy.itunesmusic.ui.common

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment(){
    val TAG = BaseFragment::class.java.simpleName

    protected val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    val viewModelObserver: Observer<BaseViewModelEvent>
        get() = Observer {
            Log.d(TAG, "Observe ViewModel Event : $it")
            when(it){
                is BaseViewModelEvent.Event -> {
                    onViewModelEvent(it.eventId, it.param)
                }
                is BaseViewModelEvent.ShowToastMessage ->{
                    Toast.makeText(it.context, it.message, Toast.LENGTH_SHORT).show()
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

    open fun onBackPressed(): Boolean {
        return false
    }

    fun observeViewModelEvent(lifecycleOwner: LifecycleOwner, viewModel: BaseViewModel) {
        Log.d(TAG, "observeViewModelEvent $viewModel")
        viewModel.viewModelEvent.observe(lifecycleOwner, viewModelObserver)
    }

    fun removeObserveViewModelEvent(lifecycleOwner: LifecycleOwner, viewModel: BaseViewModel) {
        Log.d(TAG, "removeObserveViewModelEvent $viewModel")
        viewModel.viewModelEvent.removeObservers(lifecycleOwner)
    }

    fun showProgressBar(message: String?){
        baseActivity?.showProgressBar(message)
    }

    fun hideProgressBar(message: String?){
        baseActivity?.hideProgressBar(message)
    }

    fun finishThisFragment(){
        Log.d(TAG, "finishThisFragment")

        if (isRemoving || isDetached){
            return
        }

        if (baseActivity == null){
            return
        }

        if(baseActivity?.lifecycle?.currentState == Lifecycle.State.DESTROYED || baseActivity?.isFinishing == true) {
            Log.d(TAG, "baseActivity lifecycle currentState : ${baseActivity?.lifecycle?.currentState}")
            Log.d(TAG, "baseActivity isFinishing : ${baseActivity?.isFinishing}")
            return
        }

        val fm = try {
            parentFragmentManager
        } catch (e: Throwable) {
            Log.e(TAG, Log.getStackTraceString(e))
            return
        }

        Log.d(TAG, "fragments size : ${fm.fragments.size}")

        if(fm.fragments.size > 0) {
            for(frg in fm.fragments) {
                Log.d(TAG, "$frg")
            }
        }

        if (fm.fragments.size == 1) {
            baseActivity?.finish()
            return
        }

        fm.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                super.onFragmentDetached(fm, f)
                Log.d(TAG, "onFragmentDetached")

                if (this@BaseFragment === f) {
                    fm.unregisterFragmentLifecycleCallbacks(this)

                    val superFragment = fm.fragments[fm.fragments.size - 1]
                    if (superFragment is BaseFragment) {
                        superFragment.onSubFragmentFinish(this@BaseFragment)
                    }
                }
            }
        }, false)

        runCatching {
            fm.commit(true) {
                remove(this@BaseFragment)
            }
        }
    }

    open fun onSubFragmentFinish(fragment: Fragment) {}

}