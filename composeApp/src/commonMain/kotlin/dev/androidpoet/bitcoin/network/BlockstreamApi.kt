package dev.androidpoet.bitcoin.network

import dev.androidpoet.bitcoin.network.model.AddressInfo
import dev.androidpoet.bitcoin.network.model.Transaction
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class BlockstreamApi(private val client: HttpClient) {

    suspend fun getAddressInfo(address: String): AddressInfo {
        return client.get("/address/$address").body()
    }

    suspend fun getAddressTransactions(address: String): List<Transaction> {
        return client.get("/address/$address/txs").body()
    }

    suspend fun getTransaction(txid: String): Transaction {
        return client.get("/tx/$txid").body()
    }

}