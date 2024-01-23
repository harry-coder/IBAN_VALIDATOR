package com.example.ibanvalidator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.ibanvalidator.extentions.showToastLong
import com.example.ibanvalidator.viewmodel.CurrencyViewModel
import com.example.ibanvalidator.views.CCAppBar
import com.example.ibanvalidator.views.Tabs

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyFragment : Fragment() {

    private val viewModel: CurrencyViewModel by activityViewModels<CurrencyViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.let {
            ComposeView(it).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    MainContext()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupErrorLiveData()
    }

    private fun setupErrorLiveData(){
        viewModel.failureResponseLiveData.observe(viewLifecycleOwner){
            if(it.getData()!=null){
                requireActivity()?.showToastLong(it.peekContent().message)
            }
        }
    }
    @Composable
    private fun MainContext() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { HeaderContent() },
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it),
            ) {
                AppContent()
            }
        }

    }
    @Composable
    fun AppContent() {
        Tabs(viewModel=viewModel)
    }
    @Composable
    private fun HeaderContent() {
        CCAppBar(context = requireContext(), title = "Currency Converter", showMenu = true)
    }
}