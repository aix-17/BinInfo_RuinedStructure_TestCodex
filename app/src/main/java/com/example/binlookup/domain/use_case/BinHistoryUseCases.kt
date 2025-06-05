package com.example.binlookup.domain.use_case

import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.domain.repository.BinLookupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBinHistoryUseCase @Inject constructor(
    private val repository: BinLookupRepository
) {
    operator fun invoke(): Flow<List<BinInfo>> {
        return repository.getBinHistory()
    }
}

class InsertBinHistoryUseCase @Inject constructor(
    private val repository: BinLookupRepository
) {
    suspend operator fun invoke(binInfo: BinInfo) {
        repository.insertBinHistory(binInfo)
    }
}

class DeleteBinHistoryUseCase @Inject constructor(
    private val repository: BinLookupRepository
) {
    suspend operator fun invoke(bin: String) {
        repository.deleteBinHistory(bin)
    }
}