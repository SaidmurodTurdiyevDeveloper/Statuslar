package com.example.statuslar.ui.screen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.statuslar.ui.adapter.AphorizmAdapter
import com.example.statuslar.ui.adapter.CenterZoomLayout
import com.example.statuslar.data.model.Event
import com.example.statuslar.data.source.local.room.entity.SpeachEnity
import com.example.statuslar.R
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.databinding.AphorizmLayoutBinding
import com.example.statuslar.ui.viewModel.impl.AphorizmViewModel_Impl
import com.example.statuslar.zZz_utills.PersonList
import com.example.statuslar.zZz_utills.extentions.loadObserverOnlyOneTime
import com.example.statuslar.zZz_utills.extentions.showToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception

@AndroidEntryPoint
class AphorizmFragment : Fragment(R.layout.aphorizm_layout) {
    /*
    * binding
    * */
    private var binding: AphorizmLayoutBinding? = null
    private lateinit var adapter: AphorizmAdapter
    private var personList = PersonList()
    private var id: Long = -1

    /*
    * viewModel
    * */
    private val viewModel: AphorizmViewModel_Impl by viewModels()

    /*
    * observers
    * */
    private var loadItemesObserver = Observer<Event<List<SpeachEnity>>> { event ->
        loadObserverOnlyOneTime(event) {
            adapter.submitList(it)
        }
    }
    private var openItemObserver = Observer<Event<Long>> { event ->
        loadObserverOnlyOneTime(event) {
            findNavController().navigate(AphorizmFragmentDirections.openSpeach(it))
        }
    }
    private var closeObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            findNavController().navigateUp()
        }
    }

    private var loadWiseman = Observer<Event<WiseManEntity>> { event ->
        try {
            val data = event.getDataEveryTime()
            binding?.name?.text = data.name
            if (data.pathId != "") {
                Picasso.get().load(File(data.pathId)).centerCrop().resize(300, 300).error(R.drawable.somebody)
                    .into(binding?.image)
                lifecycleScope.launch {
                    delay(100)
                    Blurry.with(requireContext())
                        .radius(12)
                        .sampling(1)
                        .from(getBitmap(File(data.pathId)))
                        .into(binding?.imageBlur)
                }
            } else {
                binding?.image?.setImageResource(personList.getWithId(data.imageId).resId)
                lifecycleScope.launch {
                    delay(100)
                    if (binding != null)
                        Blurry.with(requireContext())
                            .radius(12)
                            .sampling(1)
                            .capture(binding?.image)
                            .getAsync {
                                binding?.imageBlur?.setImageDrawable(BitmapDrawable(resources, it))
                            }
                }
            }
        } catch (e: Exception) {
            binding?.image?.setImageResource(R.drawable.somebody)
            binding?.imageBlur?.setImageResource(R.drawable.somebody)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private var smoothFirstObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            when (adapter.itemCount) {
                1 -> binding?.list?.smoothScrollToPosition(adapter.itemCount - 1)
                0 -> adapter.notifyDataSetChanged()
                else -> binding?.list?.smoothScrollToPosition(adapter.itemCount - 2)
            }
        }
    }
    private var openClickedObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            binding?.clickedView?.isClickable = true
        }
    }

    private var closeClickedObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it) {
            binding?.clickedView?.isClickable = false
        }
    }

    private var showMassageObserver = Observer<Event<String>> { event ->
        loadObserverOnlyOneTime(event) {
            requireContext().showToast(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = AphorizmLayoutBinding.bind(view)
        loadViewListeners()
        loadObservers()
        val argument = requireArguments()
        if (id == -1L) {
            id = argument.getLong("Id", -1)
            viewModel.loadViews(id)
        } else
            viewModel.loadViews(id)
    }

    private fun loadViewListeners() {
        adapter = AphorizmAdapter()
        adapter.setListenerItemClick {
            viewModel.itemTouch(it)
        }
        adapter.setListenerItemDelete {
            viewModel.delete(it)
        }
        adapter.setListenerItemfavourute {
            viewModel.update(it)
        }
        adapter.setListenerItemAdd {
            viewModel.add()
        }
        val layoutManagaer = CenterZoomLayout(requireActivity())
        layoutManagaer.orientation = LinearLayoutManager.HORIZONTAL
        layoutManagaer.reverseLayout = true
        layoutManagaer.stackFromEnd = true
        binding?.list?.layoutManager = layoutManagaer

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding?.list)
        binding?.list?.isNestedScrollingEnabled = false

        binding?.list?.adapter = adapter

        binding?.backbutton?.setOnClickListener { viewModel.back() }

        binding?.addbutton?.setOnClickListener {
            viewModel.add()
        }
    }

    private fun loadObservers() {
        viewModel.loadFirstItem.observe(viewLifecycleOwner, smoothFirstObserver)
        viewModel.close.observe(viewLifecycleOwner, closeObserver)
        viewModel.loadItems.observe(viewLifecycleOwner, loadItemesObserver)
        viewModel.loadWiseMan.observe(viewLifecycleOwner, loadWiseman)
        viewModel.openItem.observe(viewLifecycleOwner, openItemObserver)
        viewModel.openClickedLiveData.observe(viewLifecycleOwner, openClickedObserver)
        viewModel.closeClickedLiveData.observe(viewLifecycleOwner, closeClickedObserver)
        viewModel.showMassageLiveData.observe(viewLifecycleOwner, showMassageObserver)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun getBitmap(file: File): Bitmap {
        return if (Build.VERSION.SDK_INT < 28) {
            val options = BitmapFactory.Options()
            var bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
            bitmap
        } else {
            val source = ImageDecoder.createSource(file)
            val bitmap = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
            bitmap
        }
    }
}