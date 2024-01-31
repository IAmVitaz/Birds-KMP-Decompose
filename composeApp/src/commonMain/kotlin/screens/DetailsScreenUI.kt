package screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import navigation.DetailsScreenComponent

@Composable
fun DetailsScreenUI(component: DetailsScreenComponent) {
    AnimatedVisibility(visible = component.selectedBird != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            BirdImageCell(component.selectedBird!!, onBirdSelect = {})
            Row {
                Text("Bird category:")
                Text(component.selectedBird.category)
            }
            Row {
                Text("Bird author:")
                Text(component.selectedBird.author)
            }
            Button(onClick = {
                component.goBack()
            }) {
                Text("Go back")
            }
        }
    }
}