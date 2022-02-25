package com.jerry.clean_architecture_mvvm.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jerry.clean_architecture_mvvm.MainActivity


open class BaseFragment : Fragment() {

    protected lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

}