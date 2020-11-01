package com.ytowka.simplenotes.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.ytowka.simplenotes.model.Note
import com.ytowka.simplenotes.view.ListFragment

class ListViewModel() : ViewModel(){
    val list = ListLiveData<Note>()
    var lastDeletedNote: Note? = null
    fun remove(pos: Int){
        lastDeletedNote = list.value?.get(pos)
        list.remove(pos)
    }
    fun open(pos: Int){

    }
    fun addNote(){
        list.add(Note("note ${list.value?.size?.plus(1)}"))
    }
    fun undo(){
        val index = list.lastEvent.pos
        list.add(lastDeletedNote?: Note(),index)
    }
}