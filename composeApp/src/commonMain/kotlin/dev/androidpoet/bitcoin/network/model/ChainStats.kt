package dev.androidpoet.bitcoin.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChainStats(
    @SerialName("funded_txo_count")
    val fundedTxoCount: Int,
    @SerialName("funded_txo_sum")
    val fundedTxoSum: Long,
    @SerialName("spent_txo_count")
    val spentTxoCount: Int,
    @SerialName("spent_txo_sum")
    val spentTxoSum: Long,
    @SerialName("tx_count")
    val txCount: Int
)