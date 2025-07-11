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
import kotlin.math.pow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


class MainViewModel(private val repository: BitcoinRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        startRealtimeUpdates()
    }

    @OptIn(ExperimentalTime::class)
    private fun startRealtimeUpdates() {
        viewModelScope.launch {
            repository.getRealtimeUpdates().catch { e ->
                    _uiState.value = _uiState.value.copy(
                        error = "Network error: ${e.message}", isLoading = false
                    )
                }.collect { (balance, transactions) ->
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


    fun formatSatoshis(satoshis: Long): String {
        val btc = satoshis / 100_000_000.0
        return "${btc.toPlainString(8)} BTC"
    }

    // Helper extension to format to fixed decimal places
    private fun Double.toPlainString(decimalPlaces: Int): String {
        val factor = 10.0.pow(decimalPlaces)
        val rounded = kotlin.math.round(this * factor) / factor
        return buildString {
            append(rounded)
            val dotIndex = indexOf('.')
            val actualDecimals = if (dotIndex >= 0) length - dotIndex - 1 else 0
            repeat(decimalPlaces - actualDecimals) { append('0') }
            if (actualDecimals == 0) append(".").append("0".repeat(decimalPlaces))
        }
    }

}

data class UiState(
    val isLoading: Boolean = true,
    val totalBalance: Long = 0L,
    val transactions: List<Transaction> = emptyList(),
    val error: String? = null,
    val lastUpdated: Long = 0L
)
