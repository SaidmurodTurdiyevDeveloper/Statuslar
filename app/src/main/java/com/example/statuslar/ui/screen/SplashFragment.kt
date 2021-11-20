package com.example.statuslar.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.statuslar.R
import com.example.statuslar.data.model.Event
import com.example.statuslar.ui.viewModel.impl.SplashViewModel_Impl
import com.example.statuslar.zZz_utills.extentions.showToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.splash_fragment) {

    private var openMenuObserver = Observer<Event<Unit>> {
        findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMenuFragment())
    }
    private var showMassageObserver = Observer<Event<String>> {
        requireActivity().showToast(it.getDataEveryTime())
    }

    /*
    * ViewModel
    * */
    private val viewModel: SplashViewModel_Impl by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.isStateSaved
        viewModel.openMenuLiveData.observe(viewLifecycleOwner, openMenuObserver)
        viewModel.showMassageLiveData.observe(viewLifecycleOwner, showMassageObserver)
    }
}