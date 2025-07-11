import androidx.compose.ui.window.ComposeUIViewController
import dev.androidpoet.bitcoin.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
