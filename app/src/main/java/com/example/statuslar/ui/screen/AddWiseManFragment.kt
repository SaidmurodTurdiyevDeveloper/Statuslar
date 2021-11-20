package com.example.statuslar.ui.screen

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.statuslar.ui.dialog.DialogShowMessage
import com.example.statuslar.ui.dialog.PersonDialog
import com.example.statuslar.ui.viewModel.impl.WiseManViewModel_Impl
import com.example.statuslar.R
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.model.Person
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.databinding.WiseManLayoutBinding
import com.example.statuslar.zZz_utills.PersonList
import com.example.statuslar.zZz_utills.URIPathHelper
import com.example.statuslar.zZz_utills.extentions.loadObserverOnlyOneTime
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

/*
* 6/8/2021
* */
@AndroidEntryPoint
class AddWiseManFragment : Fragment(R.layout.wise_man_layout) {
    /*
    * Binding
    * */
    private var binding: WiseManLayoutBinding? = null
    private var id: Long = -1
    private var list = PersonList()
    private val uriPathHelper = URIPathHelper()
    private var personList = PersonList()

    /*
    * ViewModel
    * */
    private val viewModel: WiseManViewModel_Impl by viewModels()

    /*
    * private varable
    * */
    private val options = Permissions.Options()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        try {
            val selectedUriPath = it
            if (selectedUriPath != null) {
                val newPth = uriPathHelper.getPath(requireContext(), selectedUriPath) ?: ""
                viewModel.setImagePath(newPth)
            }
        } catch (e: Exception) {
            setErrorMessage(e.message.toString())
        }
    }

    /*
    * Observers
    * */
    private var closeObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            editTextDone(false)
            findNavController().navigateUp()
        }
    }

    private var loadDataObserver = Observer<Event<WiseManEntity>> { event ->
        loadObserverOnlyOneTime(event) {
            binding?.apply {
                nameEditext.setText(it.name)
                hashtagEdittext.setText(it.hashtag)
                infoEdittext.setText(it.info)
            }
            try {
                binding?.imageView?.setImageResource(list.getWithId(it.imageId).resId)
                if (it.pathId != "")
                    Picasso.get().load(it.pathId).resize(300, 300)
                        .error(personList.getWithId(it.imageId).resId).into(binding?.imageView)
            } catch (e: Exception) {
                binding?.imageView?.setImageResource(R.drawable.alisher)
            }
        }
    }
    private var loadErrorMessageObserver = Observer<Event<String>> { event ->
        loadObserverOnlyOneTime(event) {
            setErrorMessage(it)
        }
    }
    private var loadImageWithPathObserver = Observer<Event<String>> { event ->
        loadObserverOnlyOneTime(event) {
            try {
                Picasso.get().load(File(it)).centerCrop().error(R.drawable.error).resize(300, 300)
                    .into(binding?.imageView)
            } catch (e: Exception) {
                setErrorMessage(e.message.toString())
                viewModel.setImagePath("")
            }
        }
    }
    private var loadImageWithResIdObserver = Observer<Event<Person>> { event ->
        loadObserverOnlyOneTime(event) {
            try {
                binding?.imageView?.setImageResource(it.resId)
            } catch (e: Exception) {
                setErrorMessage(e.message.toString())
            }
        }
    }
    private var openPersonDialogObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            val dialog = PersonDialog(requireContext())
            dialog.submitListner { myData ->
                viewModel.setImageInt(myData)
            }
            dialog.submitOpenGallery {
                Permissions.check(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    null,
                    options,
                    object : PermissionHandler() {
                        override fun onGranted() {
                            getContent.launch("image/*")
                        }
                    }
                )
            }
            dialog.show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = WiseManLayoutBinding.bind(view)
        loadViewListeners()
        loadObservers()
        viewModel.setImageInt(personList.getWithId(1))
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }


    private fun setErrorMessage(message: String) {
        val dialog = DialogShowMessage(requireContext(), message)
        dialog.setListener { dialog.hide() }
        dialog.show("Yopish")
    }

    private fun loadViewListeners() {
        val argumrnts = requireArguments()
        id = argumrnts.getLong("Id", -1)
        if (id > 0) {
            viewModel.loadItem(id)
        }

        binding?.backbutton?.setOnClickListener {
            viewModel.close()
        }

        binding?.doneButton?.setOnClickListener {
            editTextDone(true)
        }

        binding?.infoEdittext?.setOnEditorActionListener { _, _, _ ->
            val name = binding?.nameEditext?.text?.toString()?.trim()
            val hashtag = binding?.hashtagEdittext?.text?.toString()?.trim()
            val info = binding?.infoEdittext?.text?.toString()?.trim()
            if (name != null && hashtag != null && info != null) {
                if (name != "") {
                    val data = WiseManEntity(0, name, hashtag, info, "", -1)
                    if (id < 0) {
                        viewModel.addData(data)
                    } else {
                        data.id = id
                        viewModel.editData(data)
                    }
                } else {
                    binding?.nameEditext?.error = "Motivator ismi kiritilmagan"
                }
            }
            false
        }
        binding?.imageView?.setOnClickListener {
            viewModel.openPersonDialog()
        }
    }

    private fun editTextDone(cond: Boolean) {
        binding?.apply {
            if (nameEditext.isActivated)
                nameEditext.onEditorAction(EditorInfo.IME_ACTION_DONE)
            if (hashtagEdittext.isActivated)
                hashtagEdittext.onEditorAction(EditorInfo.IME_ACTION_DONE)
            if (cond)
                infoEdittext.onEditorAction(EditorInfo.IME_ACTION_DONE)
            else
                infoEdittext.isActivated = false
        }
    }

    private fun loadObservers() {
        viewModel.closeLiveData.observe(viewLifecycleOwner, closeObserver)
        viewModel.loadDataLiveData.observe(viewLifecycleOwner, loadDataObserver)
        viewModel.loadErrorLiveData.observe(viewLifecycleOwner, loadErrorMessageObserver)
        viewModel.loadImagePathLiveData.observe(viewLifecycleOwner, loadImageWithPathObserver)
        viewModel.loadImageResIdLiveData.observe(viewLifecycleOwner, loadImageWithResIdObserver)
        viewModel.openPersonDialogLiveData.observe(viewLifecycleOwner, openPersonDialogObserver)
    }

}