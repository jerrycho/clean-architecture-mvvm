package com.jerry.clean_architecture_mvvm.presentation.content

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable


import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.jerry.clean_architecture_mvvm.MainActivity

import com.jerry.clean_architecture_mvvm.R
import com.jerry.clean_architecture_mvvm.databinding.FragmentContentListBinding

import com.jerry.clean_architecture_mvvm.domain.entities.Content
import com.jerry.clean_architecture_mvvm.presentation.base.BaseFragment

import com.jerry.clean_architecture_mvvm.presenation.content.adpater.ContentAdapter
import com.jerry.clean_architecture_mvvm.presentation.base.ViewState


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ContentListFragment : BaseFragment(){


    private val viewModel by viewModels<ContentListViewModel>()

    private lateinit var fragmentContentListBinding: FragmentContentListBinding
    private var contentAdapter: ContentAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentContentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_content_list, container, false)
        //fragmentContentListBinding.albumsViewModel = viewModel

        contentAdapter = ContentAdapter(object : ContentAdapter.OnItemClickListener {
            override fun onItemClicked(item: Content) {
                //doNavToDetail(item)
            }
        })

        fragmentContentListBinding.rvContent.apply {
            adapter = contentAdapter
            itemAnimator = null
            addItemDecoration(
                DividerItemDecoration(
                    requireContext().applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        fragmentContentListBinding.eBtnRefresh.setOnClickListener {
            viewModel.getContent()
        }
        
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect{ viewState->

                    when (viewState) {
                        is ViewState.Loading -> showLoadingIndicator(true)
                        is ViewState.Success -> {
                            contentAdapter?.setContentList(viewState.data!!.items)
                            showLoadingIndicator(false)
                        }
                        is ViewState.Failure -> {
                            showLoadingIndicator(false)
                           //TODO
                        }

                    }
                }
            }
        }


        return fragmentContentListBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getContent()
    }

    fun showLoadingIndicator(isShowLoading: Boolean) {
        mainActivity.showLoading(isShowLoading)
    }
}