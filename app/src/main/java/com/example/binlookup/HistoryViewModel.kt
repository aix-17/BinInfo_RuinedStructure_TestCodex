package com.example.binlookup.presentation.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.binlookup.domain.model.BinInfo
import com.example.binlookup.domain.use_case.DeleteBinHistoryUseCase
import com.example.binlookup.domain.use_case.GetBinHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getBinHistoryUseCase: GetBinHistoryUseCase,
    private val deleteBinHistoryUseCase: DeleteBinHistoryUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HistoryState())
    val state: State<HistoryState> = _state

    init {
        getBinHistory()
    }

    fun onEvent(event: HistoryEvent) {
        when (event) {
            is HistoryEvent.DeleteBinHistory -> {
                viewModelScope.launch {
                    deleteBinHistoryUseCase(event.bin)
                }
            }
        }
    }

    private fun getBinHistory() {
        getBinHistoryUseCase().onEach { binHistory ->
            _state.value = _state.value.copy(
                binHistory = binHistory
            )
        }.launchIn(viewModelScope)
    }
}

data class HistoryState(
    val binHistory: List<BinInfo> = emptyList()
)

sealed class HistoryEvent {
    data class DeleteBinHistory(val bin: String) : HistoryEvent()
}