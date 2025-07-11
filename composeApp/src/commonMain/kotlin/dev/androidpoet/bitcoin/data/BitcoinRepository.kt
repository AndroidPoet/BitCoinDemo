package dev.androidpoet.bitcoin.data

import dev.androidpoet.bitcoin.network.BlockstreamApi
import dev.androidpoet.bitcoin.network.model.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class BitcoinRepository(private val api: BlockstreamApi) {

    private val watchedAddresses = listOf(
        "tb1qtzrhlwxqcsufs8hvg4c3w33utf9hat4x9xlrf7",
        "tb1q9q8mmlwuxy75utn6v0dr72n3s5pec436dzkchu",
        "tb1q5yuzy28r9ca75t7nle34mecummx3ghh2huxu6u",
        "tb1q96qev5k0ps9xhxl27n43l47q0hk4g98g02qvgf",
        "tb1qkuv67xamatj303ugvx6u7x2jl2cmn2kguxrwep",
        "tb1q0gll6nh3fs2vclk72n83htm89vxmvgnekcq80q",
        "tb1qn4kz6xessgrsresex8vlmn2mehkwgk6x6ttt4e",
        "tb1q7nhqtca98nezzce8wluyuaxez9w4lnmgrfjvwc",
        "tb1queu8gdf2y0870de28fdj0q7gt3lszppp06p0z6",
        "tb1quwf0s47w48pdlhs24m50n0m4h70ll2melgezcz"
    )

    suspend fun getTotalBalance(): Long = coroutineScope {
        try {
            val balances = watchedAddresses.map { address ->
                async {
                    try {
                        api.getAddressInfo(address).totalBalance
                    } catch (e: Exception) {
                        // TODO: Add proper error logging
                        0L
                    }
                }
            }
            balances.awaitAll().sum()
        } catch (e: Exception) {
            0L
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun getAllTransactions(): List<Transaction> = coroutineScope {
        try {
            val allTransactions = watchedAddresses.map { address ->
                async {
                    try {
                        api.getAddressTransactions(address)
                    } catch (e: Exception) {
                        emptyList<Transaction>()
                    }
                }
            }

            allTransactions.awaitAll().flatten().distinctBy { it.txid }.sortedByDescending { tx ->
                // Sort by block time for confirmed, or current time for unconfirmed
                tx.status.blockTime ?: (Clock.System.now().epochSeconds / 1000)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getRealtimeUpdates(): Flow<Pair<Long, List<Transaction>>> = flow {
        while (true) {
            try {
                val balance = getTotalBalance()
                val transactions = getAllTransactions()
                emit(balance to transactions)
                delay(30_000)
            } catch (e: Exception) {
                delay(30_000)
            }
        }
    }
}