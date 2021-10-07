package com.archestro.grocery.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.archestro.grocery.domain.usecases.base.Outcome
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ScopeFragment:Fragment(),CoroutineScope {

    private var baseViewModel: BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseViewModel = getViewModel()
        baseViewModel?.outcomeLiveData?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Outcome.Start -> {
//                    (activity as MainActivity).showProgressLoader(true)
                }
                is Outcome.End -> {
//                    (activity as MainActivity).showProgressLoader(false)
                }
                is Outcome.Failure -> {
                    Toast.makeText(
                        activity,
                        "Some error occurred, Please try later",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Outcome.NetworkError -> {
                    Toast.makeText(
                        activity,
                        "No Internet Connectivity",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job+ Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job= Job()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
    abstract fun getViewModel(): BaseViewModel?
}