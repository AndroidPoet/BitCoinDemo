package dev.androidpoet.bitcoin

import androidx.compose.runtime.Composable
import dev.androidpoet.bitcoin.di.appModule
import dev.androidpoet.bitcoin.theme.AppTheme
import dev.androidpoet.bitcoin.ui.compoents.MainScreen
import dev.androidpoet.bitcoin.viewmodel.MainViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
internal fun App() = AppTheme {
    KoinApplication(application = {
        modules(appModule())
    }) {
        val viewModel = koinViewModel<MainViewModel>()
        MainScreen(viewModel = viewModel)
    }
}
