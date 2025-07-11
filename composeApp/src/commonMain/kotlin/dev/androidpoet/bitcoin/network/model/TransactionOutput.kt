package dev.androidpoet.bitcoin.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionOutput(
    val scriptpubkey: String,
    @SerialName("scriptpubkey_asm")
    val scriptpubkeyAsm: String,
    @SerialName("scriptpubkey_type")
    val scriptpubkeyType: String,
    @SerialName("scriptpubkey_address")
    val scriptpubkeyAddress: String? = null,
    val value: Long
)