package com.ytowka.simplenotes.view

import android.app.Application
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ytowka.simplenotes.R
import com.ytowka.simplenotes.model.Note
import com.ytowka.simplenotes.viewmodel.ListLiveData
import com.ytowka.simplenotes.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    lateinit var viewModel: ListViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
       /* viewModel.showSnackBar = {
            val snackbar: Snackbar = Snackbar.make(requireView(),"deleted", Snackbar.LENGTH_LONG).setAction("undo") {
                viewModel.undo()
            }
            snackbar.show()
        }*/
        val listAdapter = ListAdapter(viewModel)

        viewModel.list.observe(viewLifecycleOwner){
            val lastEvent = viewModel.list.lastEvent
            when(lastEvent.type){
                ListLiveData.EventType.REMOVE -> listAdapter.removeUpdate(lastEvent.pos)
                ListLiveData.EventType.ADD -> listAdapter.addUpdate(lastEvent.pos)
                ListLiveData.EventType.UPDATE -> listAdapter.update()
            }
        }
        noteList.adapter = listAdapter
        noteList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        /*val touchHelperCallback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
               val moves: Int = ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                return makeMovementFlags(moves,0)
            }
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                viewModel.list.swap(viewHolder.adapterPosition,target.adapterPosition)
                (recyclerView.adapter as ListAdapter).swapUpdate(viewHolder.adapterPosition,target.adapterPosition)
               return true
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

        }
        ItemTouchHelper(touchHelperCallback).attachToRecyclerView(noteList)*/
        fab_add.setOnClickListener {
            viewModel.addNote()
        }
    }
}