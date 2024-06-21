package com.deras.id

import android.app.Application
import androidx.lifecycle.LiveData
import com.deras.id.database.DerasDB
import com.deras.id.database.HistoryDao
import com.deras.id.database.HistoryEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepo(application: Application) {
    private val historyDao: HistoryDao = DerasDB.getDatabase(application).historyDao()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun addHistory(historyEntity: HistoryEntity) {
        historyDao.addHistory(historyEntity)
    }

    fun getHistory(): LiveData<List<HistoryEntity>> = historyDao.getHistory()

    suspend fun removeAllHistory() {
        historyDao.removeAllHistory()
    }
}
