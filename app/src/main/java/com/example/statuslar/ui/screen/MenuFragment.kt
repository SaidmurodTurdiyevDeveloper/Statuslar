package com.example.statuslar.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.statuslar.ui.viewModel.impl.MenuViewModel_Impl
import com.example.statuslar.data.model.Event
import com.example.statuslar.R
import com.example.statuslar.databinding.MenuLayoutBinding
import com.example.statuslar.zZz_utills.extentions.loadObserverOnlyOneTime
import dagger.hilt.android.AndroidEntryPoint

/*
* 6/3/2021 (DD/MM/YYYY)
* */
@AndroidEntryPoint
class MenuFragment : Fragment(R.layout.menu_layout) {
    /*
    * Binding
    * */
    private var binding: MenuLayoutBinding? = null

    /*
    * ViewModel
    * */
    private val viewModel: MenuViewModel_Impl by viewModels()

    /*
    * Observers
    * */
    private val openHikmatlarObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            findNavController().navigate(MenuFragmentDirections.openMainFragment())
        }
    }

    private val openAppInfoObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            findNavController().navigate(MenuFragmentDirections.openAppInfoFragment())
        }
    }
    private val quitObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = MenuLayoutBinding.bind(view)
        loading()
        registerObserver()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    /*
    * Private Metod
    * */
    @SuppressLint("ClickableViewAccessibility")
    private fun loading() {
        binding?.apply {
            hikmatlarButton.setOnClickListener {
                viewModel.openHikmatlar()
            }

            appInfoButton.setOnClickListener {
                viewModel.openAppInfo()
            }

            quitButton.setOnClickListener { viewModel.quit() }
        }
    }

    private fun registerObserver() {
        viewModel.openHikmatlarLiveData.observe(viewLifecycleOwner, openHikmatlarObserver)
        viewModel.openAppInfoLiveData.observe(viewLifecycleOwner, openAppInfoObserver)
        viewModel.quitLiveData.observe(viewLifecycleOwner, quitObserver)
    }
}