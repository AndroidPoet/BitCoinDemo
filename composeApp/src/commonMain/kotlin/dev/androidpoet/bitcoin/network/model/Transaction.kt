package dev.androidpoet.bitcoin.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val txid: String,
    val version: Int,
    val locktime: Long,
    val vin: List<TransactionInput>,
    val vout: List<TransactionOutput>,
    val size: Int,
    val weight: Int,
    val fee: Long,
    val status: TransactionStatus
) {
    val isConfirmed: Boolean
        get() = status.confirmed

    val confirmationStatus: String
        get() = if (status.confirmed) {
            "Confirmed in block ${status.blockHeight}"
        } else {
            "Unconfirmed"
        }
}
