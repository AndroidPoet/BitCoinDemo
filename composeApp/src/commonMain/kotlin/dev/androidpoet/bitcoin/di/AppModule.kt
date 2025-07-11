package dev.androidpoet.bitcoin.di

import dev.androidpoet.bitcoin.data.BitcoinRepository
import dev.androidpoet.bitcoin.network.BlockstreamApi
import dev.androidpoet.bitcoin.network.provideKtorClient
import dev.androidpoet.bitcoin.viewmodel.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


fun appModule() = module {
    single { provideKtorClient() }
    viewModel {
        MainViewModel(get())
    }
    single { BitcoinRepository(get()) }
    single { BlockstreamApi(get()) }
}

