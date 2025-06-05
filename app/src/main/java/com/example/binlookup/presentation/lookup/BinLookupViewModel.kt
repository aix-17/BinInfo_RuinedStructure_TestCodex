package com.example.binlookup.presentation.lookup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlookup.core.util.Resource
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.domain.use_case.GetBinInfoUseCase
import com.example.binlookup.domain.use_case.InsertBinHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinLookupViewModel @Inject constructor(
    private val getBinInfoUseCase: GetBinInfoUseCase,
    private val insertBinHistoryUseCase: InsertBinHistoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(BinLookupState())
    val state: State<BinLookupState> = _state

    fun onEvent(event: BinLookupEvent) {
        when (event) {
            is BinLookupEvent.EnteredBin -> {
                _state.value = _state.value.copy(bin = event.value)
            }
            is BinLookupEvent.LookupBin -> {
                if (_state.value.bin.length < 6) {
                    _state.value = _state.value.copy(
                        error = "BIN номер должен содержать минимум 6 цифр"
                    )
                    return
                }
                lookupBin()
            }
            is BinLookupEvent.ClearResult -> {
                _state.value = _state.value.copy(
                    binInfo = null,
                    error = null
                )
            }
        }
    }

    private fun lookupBin() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = null
                )

                val result = getBinInfoUseCase(_state.value.bin)
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { binInfo ->
                            try {
                                insertBinHistoryUseCase(binInfo)
                                _state.value = _state.value.copy(
                                    binInfo = binInfo,
                                    isLoading = false,
                                    error = null
                                )
                            } catch (e: Exception) {
                                _state.value = _state.value.copy(
                                    isLoading = false,
                                    error = "Ошибка при сохранении в историю: ${e.localizedMessage}"
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = result.message ?: "Произошла неизвестная ошибка"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Произошла ошибка: ${e.localizedMessage}"
                )
            }
        }
    }
}