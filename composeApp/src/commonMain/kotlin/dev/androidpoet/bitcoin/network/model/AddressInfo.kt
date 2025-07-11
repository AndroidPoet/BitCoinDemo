package dev.androidpoet.bitcoin.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressInfo(
    val address: String,
    @SerialName("chain_stats")
    val chainStats: ChainStats,
    @SerialName("mempool_stats")
    val mempoolStats: ChainStats
) {
    val totalBalance: Long
        get() = chainStats.fundedTxoSum + mempoolStats.fundedTxoSum -
                chainStats.spentTxoSum - mempoolStats.spentTxoSum
}