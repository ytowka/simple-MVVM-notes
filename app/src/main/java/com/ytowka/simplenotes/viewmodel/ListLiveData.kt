package com.ytowka.simplenotes.viewmodel

import androidx.lifecycle.LiveData
import com.ytowka.simplenotes.model.Note

class ListLiveData<T> : LiveData<List<T>>(){
    private var mList: MutableList<T> = mutableListOf()
    init {
        value = mList

    }
    var lastEvent = Event(EventType.UPDATE,-1)
    fun add(obj: T, pos: Int = value?.size ?: 0){
        lastEvent = Event(EventType.ADD, pos)
        mList.add(pos,obj)
        value = mList
    }
    fun swap(posFrom: Int, posTo: Int){
        val buffer = mList[posFrom]
        mList[posFrom] = mList[posTo]
        mList[posTo] = buffer
    }
    fun remove(pos: Int = value?.size ?: 0){
        lastEvent = Event(EventType.REMOVE, pos)
        mList.removeAt(pos)
        value = mList
    }
    override fun onActive() {
        lastEvent = Event(EventType.UPDATE,-1)
        super.onActive()
    }
    data class Event(val type: EventType, val pos: Int)
    enum class EventType{
        ADD, REMOVE, UPDATE
    }
}