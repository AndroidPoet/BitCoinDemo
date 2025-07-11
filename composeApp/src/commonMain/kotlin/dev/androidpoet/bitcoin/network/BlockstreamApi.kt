package dev.androidpoet.bitcoin.network

import dev.androidpoet.bitcoin.network.model.AddressInfo
import dev.androidpoet.bitcoin.network.model.Transaction
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BlockstreamApi(private val client: HttpClient) {

    suspend fun getAddressInfo(address: String): AddressInfo {
        return client.get("$TESTNET_BASE_URL/address/$address").body()
    }

    suspend fun getAddressTransactions(address: String): List<Transaction> {
        return client.get("$TESTNET_BASE_URL/address/$address/txs").body()
    }

    suspend fun getTransaction(txid: String): Transaction {
        return client.get("$TESTNET_BASE_URL/tx/$txid").body()
    }

    companion object {
        const val TESTNET_BASE_URL = "https://blockstream.info/testnet/api"
    }

}