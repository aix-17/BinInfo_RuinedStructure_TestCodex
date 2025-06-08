package com.example.binlookup.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.binlookup.data.local.dao.BinHistoryDao
import com.example.binlookup.data.local.entity.BinHistoryEntity

@Database(
    entities = [BinHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BinLookupDatabase : RoomDatabase() {
    
    abstract fun binHistoryDao(): BinHistoryDao
    
    companion object {
        const val DATABASE_NAME = "bin_lookup_database"
    }
}