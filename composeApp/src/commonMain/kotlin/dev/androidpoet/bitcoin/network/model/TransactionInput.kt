package dev.androidpoet.bitcoin.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionInput(
    val txid: String,
    val vout: Int,
    val prevout: TransactionOutput?,
    val scriptsig: String,
    val witness: List<String>? = null,
    val sequence: Long,
    @SerialName("is_coinbase")
    val isCoinbase: Boolean? = null
)