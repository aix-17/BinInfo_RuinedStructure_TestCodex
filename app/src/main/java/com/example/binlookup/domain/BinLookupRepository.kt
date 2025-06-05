package com.example.binlookup.domain.repository

import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface BinLookupRepository {
    suspend fun getBinInfo(bin: String): Resource<BinInfo>
    fun getBinHistory(): Flow<List<BinInfo>>
    suspend fun insertBinHistory(binInfo: BinInfo)
    suspend fun deleteBinHistory(bin: String)
}