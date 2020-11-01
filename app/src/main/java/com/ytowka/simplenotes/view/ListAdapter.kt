package com.ytowka.simplenotes.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ytowka.simplenotes.R
import com.ytowka.simplenotes.databinding.ItemNoteBinding
import com.ytowka.simplenotes.model.Note
import com.ytowka.simplenotes.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.item_note.view.*

class ListAdapter(val viewModel: ListViewModel) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    val list = viewModel.list.value?: listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int = list.size

    fun addUpdate(index: Int){
        notifyItemInserted(index)
    }
    fun swapUpdate(fromPos: Int, toPos: Int){
        notifyItemMoved(fromPos,toPos)
    }
    fun update(){
        notifyDataSetChanged()
    }
    fun removeUpdate(index: Int){
        notifyItemRemoved(index)
    }

    inner class ViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener, View.OnClickListener{
        val view = binding.root
        init {
            view.apply {
                setOnLongClickListener(this@ViewHolder)
                setOnClickListener(this@ViewHolder)
            }
        }
        fun bind(note: Note){
            binding.note = note
            binding.executePendingBindings()
        }
        override fun onLongClick(p0: View?): Boolean {
            viewModel.remove(adapterPosition)
            Log.i("debug","deleted")
            return true
        }
        override fun onClick(p0: View?) {
            viewModel.open(adapterPosition)
        }
    }
}