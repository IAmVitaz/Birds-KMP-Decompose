import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.RootComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import screens.DetailsScreenUI
import screens.ListScreenUI


@Composable
fun BirdAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = RoundedCornerShape(0.dp),
            medium = RoundedCornerShape(0.dp),
            large = RoundedCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(root: RootComponent) {
    BirdAppTheme {
        Surface(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
            val childStack by root.childStack.subscribeAsState()
            Children(
                stack = childStack,
                animation = stackAnimation(
                    slide()
                )
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.ListScreen -> ListScreenUI(instance.component)
                    is RootComponent.Child.DetailsScreen -> DetailsScreenUI(instance.component)
                }
            }
        }
    }
}
