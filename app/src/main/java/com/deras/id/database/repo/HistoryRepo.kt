package com.deras.id.database.repo

import android.content.Context
import com.deras.id.database.DerasDB
import com.deras.id.database.HistoryEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HistoryRepo (context: Context) {
    private val historyDao = DerasDB.getDatabase(context.applicationContext).historyDao()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

        suspend fun addHistory(historyentity: HistoryEntity) {
        historyDao.addHistory(historyentity)
    }

    fun getHistory(): List<HistoryEntity> = historyDao.getHistory()

    fun removeAllHistory() {
        executorService.execute { historyDao.removeAllHistory() }
    }
}