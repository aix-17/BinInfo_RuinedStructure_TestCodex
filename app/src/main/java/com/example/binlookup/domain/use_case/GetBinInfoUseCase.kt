package com.example.binlookup.domain.use_case

import android.content.Context
import com.example.binlookup.core.util.Resource
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.domain.repository.BinLookupRepository
import com.example.binlookup.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetBinInfoUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: BinLookupRepository
) {
    suspend operator fun invoke(bin: String): Resource<BinInfo> {
        if (bin.isBlank()) {
            return Resource.Error(context.getString(R.string.bin_error_empty))
        }
        
        if (bin.length < 6) {
            return Resource.Error(context.getString(R.string.bin_error_length))
        }
        
        if (!bin.all { it.isDigit() }) {
            return Resource.Error(context.getString(R.string.bin_error_digits))
        }
        
        return repository.getBinInfo(bin)
    }
}