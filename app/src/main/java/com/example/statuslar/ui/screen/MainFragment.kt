package com.example.statuslar.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statuslar.ui.adapter.RecyclerViewAdapter
import com.example.statuslar.data.model.Event
import com.example.statuslar.ui.dialog.DialogShowMessage
import com.example.statuslar.R
import com.example.statuslar.data.model.EventWithBlock
import com.example.statuslar.data.source.local.room.entity.WiseManEntity
import com.example.statuslar.databinding.MainLayoutBinding
import com.example.statuslar.ui.viewModel.impl.MainViewModel_Impl
import com.example.statuslar.zZz_utills.extentions.loadObserverOnlyOneTime
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_layout) {
    /*
    * binding
    * */
    private var binding: MainLayoutBinding? = null

    /*
    * Private varable
    * */
    private lateinit var adapter: RecyclerViewAdapter

    /*
    * ViewModel
    * */
    private val viewModel: MainViewModel_Impl by viewModels()

    /*
    * Observers
    * */
    private var backObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it){
            findNavController().navigateUp()
        }
    }
    private var loadItemsObserver = Observer<Event<List<WiseManEntity>>> { event ->
        loadObserverOnlyOneTime(event){
            adapter.submitList(it)
        }
    }
    private var addItemObserver = Observer<Event<Unit>> {
        loadObserverOnlyOneTime(it){
            findNavController().navigate(MainFragmentDirections.openAddOrEditFragment())
        }
    }
    private var editItemObserver = Observer<Event<Long>> { event ->
        loadObserverOnlyOneTime(event){
            findNavController().navigate(MainFragmentDirections.openAddOrEditFragment(it))
        }
    }

    private var deleteObserver = Observer<EventWithBlock<WiseManEntity, WiseManEntity, Unit>> {event->
        loadObserverOnlyOneTime(event){
            val dialog = DialogShowMessage(requireContext(), it.name + " o`chirilsinmi")
            dialog.setListener {
                event.getBlock().invoke(it)
            }
            dialog.show("O`chirish")
        }
    }
    private var itemTouchObserver = Observer<Event<Long>> { event ->
        loadObserverOnlyOneTime(event){
            findNavController().navigate(MainFragmentDirections.openAphorizmFragment(it))
        }
    }

    /*
    * Override Functions
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = MainLayoutBinding.bind(view)
        loadViewListeneres()
        loadObservers()
        viewModel.loadItems()
    }

    /*
    * Private Functions
    * */
    private fun loadViewListeneres() {
        adapter = RecyclerViewAdapter(requireContext())
        binding?.apply {
            list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            list.adapter = adapter
            backbutton.setOnClickListener {
                viewModel.back()
            }
            addButton.setOnClickListener {
                viewModel.add()
            }
        }
        adapter.setListenerItemClick {
            viewModel.openAphorizmFragment(it)
        }
        adapter.setListenerItemLongClick { data ->
            data.submitDeleteListener { man ->
                viewModel.delete(man)
            }
            data.submitUpdateListener { id ->
                viewModel.edit(id)
            }
            data.show()
        }
    }

    private fun loadObservers() {
        viewModel.backLivedata.observe(viewLifecycleOwner, backObserver)
        viewModel.addButtonLiveData.observe(viewLifecycleOwner, addItemObserver)
        viewModel.deleteLiveData.observe(viewLifecycleOwner, deleteObserver)
        viewModel.editLivedata.observe(viewLifecycleOwner, editItemObserver)
        viewModel.openAphorizmFragment.observe(viewLifecycleOwner, itemTouchObserver)
        viewModel.loadItemsLiveData.observe(viewLifecycleOwner, loadItemsObserver)
    }
}