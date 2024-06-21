package com.deras.id.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.deras.id.HistoryRepo
import com.deras.id.database.HistoryEntity
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val historyRepo = HistoryRepo(application)
    val historyList: LiveData<List<HistoryEntity>> = historyRepo.getHistory()

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
}
