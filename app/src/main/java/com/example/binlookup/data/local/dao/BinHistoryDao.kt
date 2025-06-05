package com.example.binlookup.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.binlookup.data.local.entity.BinHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BinHistoryDao {
    
    @Query("SELECT * FROM bin_history ORDER BY timestamp DESC")
    fun getAllBinHistory(): Flow<List<BinHistoryEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBinHistory(binHistory: BinHistoryEntity)
    
    @Query("DELETE FROM bin_history WHERE bin = :bin")
    suspend fun deleteBinHistory(bin: String)
    
    @Query("SELECT * FROM bin_history WHERE bin = :bin LIMIT 1")
    suspend fun getBinHistoryByBin(bin: String): BinHistoryEntity?
}