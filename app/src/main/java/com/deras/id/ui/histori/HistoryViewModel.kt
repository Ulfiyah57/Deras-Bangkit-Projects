package com.deras.id.ui.history

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.deras.id.database.HistoryEntity
import com.deras.id.database.repo.HistoryRepo
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : ViewModel(), ViewModelProvider.Factory {
    private val historyRepo: HistoryRepo = HistoryRepo(application)

    var historyList: MutableLiveData<List<HistoryEntity>> = MutableLiveData()

    init {
        historyList.value = getHistory()
    }

    fun addHistory(historyEntity: HistoryEntity) {
        viewModelScope.launch {
            historyRepo.addHistory(historyEntity)
        }
    }

    fun removeAllHistory() {
        viewModelScope.launch {
            historyRepo.removeAllHistory()
        }
    }

    private fun getHistory(): List<HistoryEntity> = historyRepo.getHistory()
}