package com.example.binlookup.presentation.lookup

import com.example.binlookup.domain.model.BinInfo

data class BinLookupState(
    val bin: String = "",
    val binInfo: BinInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class BinLookupEvent {
    data class EnteredBin(val value: String) : BinLookupEvent()
    object LookupBin : BinLookupEvent()
    object ClearResult : BinLookupEvent()
}