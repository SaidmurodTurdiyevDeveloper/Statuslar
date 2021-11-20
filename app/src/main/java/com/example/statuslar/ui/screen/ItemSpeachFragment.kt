package com.example.statuslar.ui.screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.statuslar.R
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.databinding.LayoutSpeachBinding
import com.example.statuslar.ui.dialog.DialogChooseTwoItem
import com.example.statuslar.ui.viewModel.impl.SpeachViewModel_Impl
import com.example.statuslar.zZz_utills.extentions.loadObserverOnlyOneTime
import com.example.statuslar.zZz_utills.extentions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemSpeachFragment : Fragment(R.layout.layout_speach) {
    /*
    * binding
    * */
    private var binding: LayoutSpeachBinding? = null
    private var id: Long = -1L

    /*
    * ViewModel
    * */
    private val viewModel: SpeachViewModel_Impl by viewModels()

    /*
    * Observers
    * */
    private var closeObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            findNavController().navigateUp()
        }
    }
    private var loadItemsObserver = Observer<Event<SpeachEnity>> { event ->
        loadObserverOnlyOneTime(event) {
            binding?.editText?.setText(it.text)
            when (it.favourite) {
                0 -> {
                    changeFavourute(false)
                }
                1 -> {
                    changeFavourute(true)
                }
            }
        }
    }
    private var openChooseMessageObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            val dialog = DialogChooseTwoItem(
                requireContext(),
                "Siz O`zgarishlarni amalga oshirdingiz bularni saqlashni istaysizmi ?"
            )
            dialog.setListenerOne("Chiqish") {
                dialog.hide()
                viewModel.back()
            }
            dialog.setListenerTwo("Saqlash") {
                dialog.hide()
                viewModel.save(SpeachEnity(0, binding?.editText?.text?.toString() ?: "None", 0, 0), id)
                viewModel.back()
            }
            dialog.show()
        }
    }
    private var changefavouruteObserver = Observer<Event<Boolean>> {
        changeFavourute(it.getDataEveryTime())
    }
    private var changeTextObserver = Observer<Event<Boolean>> {
        if (it.getDataEveryTime()) {
            binding?.doneItem?.progress = 0F
            binding?.doneItem?.pauseAnimation()
        } else {
            binding?.doneItem?.speed = 1F
            binding?.doneItem?.playAnimation()
        }
    }
    private var showMassageObserver = Observer<Event<String>> { event ->
        loadObserverOnlyOneTime(event) {
            requireActivity().showToast(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = LayoutSpeachBinding.bind(view)
        loadingViews()
        loadingObservers()
        viewModel.loadSpeach(id)
    }

    /*
    * Private method
    * */
    private fun changeFavourute(cond: Boolean) {
        if (cond) {
            binding?.favourite?.speed = 1F
            binding?.favourite?.playAnimation()
        } else {
            binding?.favourite?.progress = 0F
            binding?.favourite?.pauseAnimation()
        }
    }

    private fun loadingViews() {
        val arguments = requireArguments()
        id = arguments.getLong("Id")
        binding?.doneItem?.setOnClickListener {
            val text = binding?.editText?.text.toString()
            binding?.editText?.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewModel.save(SpeachEnity(0, text, 0, 0), id)
        }
        binding?.backbutton?.setOnClickListener {
            binding?.editText?.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewModel.check(binding?.editText?.text?.toString() ?: "None")
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding?.editText?.onEditorAction(EditorInfo.IME_ACTION_DONE)
                viewModel.check(binding?.editText?.text?.toString() ?: "None")
            }
        })
        binding?.delete?.setOnClickListener {
            binding?.editText?.isActivated = false
            viewModel.delete(id)
        }
        binding?.favourite?.setOnClickListener {
            binding?.editText?.onEditorAction(EditorInfo.IME_ACTION_DONE)
            viewModel.favourute(id)
        }
        binding?.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.changeData(binding?.editText?.text?.toString() ?: s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun loadingObservers() {
        viewModel.backLivedata.observe(viewLifecycleOwner, closeObserver)
        viewModel.changeFavouruteLiveData.observe(viewLifecycleOwner, changefavouruteObserver)
        viewModel.changeTextLiveData.observe(viewLifecycleOwner, changeTextObserver)
        viewModel.loadItemLiveData.observe(viewLifecycleOwner, loadItemsObserver)
        viewModel.openChooseMessageDialogLiveData.observe(
            viewLifecycleOwner,
            openChooseMessageObserver
        )
        viewModel.showMassageLiveData.observe(viewLifecycleOwner, showMassageObserver)
    }
}