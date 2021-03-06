package com.neouto.kotlinnotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.neouto.kotlinnotes.data.model.Note
import com.neouto.kotlinnotes.data.repository.NoteRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class NoteViewModel(private val repository: NoteRepository): ViewModel() {

    // --> all note live data
    val fetchAllNoteLiveData: LiveData<List<Note>> by lazy {
        repository.fetchAllNotesLiveData
    }

    // --> insert note to database
    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNote(note)
        }
    }

    // --> delete note form database
    fun deleteNote(note: Note) {
        repository.deleteNote(note)
    }

    // --> delete all database
    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

}

class NoteViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}