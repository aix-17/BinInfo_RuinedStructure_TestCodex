package com.example.binlookup.domain.use_case

import com.example.binlookup.core.util.Resource
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.domain.repository.BinLookupRepository
import javax.inject.Inject

class GetBinInfoUseCase @Inject constructor(
    private val repository: BinLookupRepository
) {
    suspend operator fun invoke(bin: String): Resource<BinInfo> {
        if (bin.isBlank()) {
            return Resource.Error("BIN номер не может быть пустым")
        }
        
        if (bin.length < 6) {
            return Resource.Error("BIN номер должен содержать минимум 6 цифр")
        }
        
        if (!bin.all { it.isDigit() }) {
            return Resource.Error("BIN номер должен содержать только цифры")
        }
        
        return repository.getBinInfo(bin)
    }
}