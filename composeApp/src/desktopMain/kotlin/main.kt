import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.sample.app.runOnUiThread
import navigation.RootComponent
import com.arkivanov.decompose.DefaultComponentContext



fun main() {
    val lifecycle = LifecycleRegistry()

    // Always create the root component outside Compose on the UI thread
    val root =
        runOnUiThread {
            RootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
            )
        }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Birds"
        ) {
            App(root)
        }
    }
}

//@Preview
//@Composable
//fun AppDesktopPreview() {
//    App()
//}