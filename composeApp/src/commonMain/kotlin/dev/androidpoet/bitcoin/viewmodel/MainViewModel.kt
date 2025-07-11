package dev.androidpoet.bitcoin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androidpoet.bitcoin.data.BitcoinRepository
import dev.androidpoet.bitcoin.network.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class UiState(
    val isLoading: Boolean = true,
    val totalBalance: Long = 0L,
    val transactions: List<Transaction> = emptyList(),
    val error: String? = null,
    val lastUpdated: Long = 0L
)

class MainViewModel(private val repository: BitcoinRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        startRealtimeUpdates()
    }

    @OptIn(ExperimentalTime::class)
    private fun startRealtimeUpdates() {
        viewModelScope.launch {
            repository.getRealtimeUpdates()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = "Network error: ${e.message}",
                        isLoading = false
                    )
                }
                .collect { (balance, transactions) ->
                    _uiState.value = UiState(
                        isLoading = false,
                        totalBalance = balance,
                        transactions = transactions,
                        error = null,
                        lastUpdated = Clock.System.now().epochSeconds
                    )
                }
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        startRealtimeUpdates()
    }


}