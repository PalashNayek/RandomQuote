package com.dac.randomquote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dac.randomquote.models.QuoteList
import com.dac.randomquote.repository.QuoteRepository
import com.dac.randomquote.repository.Response
import kotlinx.coroutines.launch

class MainViewModels(private val repository: QuoteRepository) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.getQuotes(1)
        }
    }

    val quotes : LiveData<Response<QuoteList>>
    get() = repository.quotes
}