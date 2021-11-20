package com.example.statuslar.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.statuslar.R
import com.example.statuslar.databinding.AppInfoLayoutBinding

class AppInfoFragment : Fragment(R.layout.app_info_layout) {
    private var binding: AppInfoLayoutBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = AppInfoLayoutBinding.bind(view)
        binding?.backbutton?.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}