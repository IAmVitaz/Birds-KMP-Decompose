import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

class BirdsDetailsScreen(
    val selectedBird: BirdImage
): Screen {
    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BirdsDetailsPage(selectedBird = selectedBird)
        }
    }
}

@Composable
fun BirdsDetailsPage(
    selectedBird: BirdImage
) {
    AnimatedVisibility(visible = selectedBird != null) {
        Column {
            BirdImageCell(selectedBird!!, onBirdSelect = {})
            Row {
                Text("Bird category:")
                Text(selectedBird.category)
            }
            Row {
                Text("Bird author:")
                Text(selectedBird.author)
            }
        }
    }
}
