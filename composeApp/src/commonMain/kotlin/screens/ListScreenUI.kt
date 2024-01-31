package screens

import models.BirdImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import navigation.BirdsUiState
import navigation.ListScreenComponent
import navigation.ListScreenEvent

@Composable
fun ListScreenUI(component: ListScreenComponent) {

    val uiState by component.uiState.subscribeAsState()

    LaunchedEffect(component) {
        if (uiState.images.isEmpty()) {
            component.updateImages()
        }
    }
    BirdsPage(
        uiState,
        onCategorySelect = {
            component.onEvent(ListScreenEvent.SelectBirdCategory(it))
        },
        onBirdSelect = {
            component.onEvent(ListScreenEvent.SelectBird(it))
        }
    )
}

@Composable
fun BirdsPage(
    uiState: BirdsUiState,
    onCategorySelect: (String) -> Unit,
    onBirdSelect: (BirdImage) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxWidth().padding(5.dp),
        ) {
            for (category in uiState.categories) {
                Button(
                    onClick = { onCategorySelect(category) },
                    modifier = Modifier.aspectRatio(1.0f).weight(1.0f)
                ) {
                    Text(category)
                }
            }
        }

        AnimatedVisibility(visible = uiState.selectedImages.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(180.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
            ) {

                items(uiState.selectedImages) { image ->
                    BirdImageCell(image, onBirdSelect = onBirdSelect)
                }
            }
        }
    }
}

@Composable
fun BirdImageCell(
    image: BirdImage,
    onBirdSelect: (BirdImage) -> Unit
) {
    KamelImage(
        resource = asyncPainterResource("https://sebastianaigner.github.io/demo-image-api/${image.path}"),
        contentDescription = "${image.category} by ${image.author}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
            .clickable { onBirdSelect(image) },
    )
}
