package com.deras.id.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getHistory(): LiveData<List<HistoryEntity>>

    @Query("DELETE FROM history")
    suspend fun removeAllHistory()
}
