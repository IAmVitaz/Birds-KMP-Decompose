import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

class BirdsListScreen: Screen  {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val birdsViewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        val uiState by birdsViewModel.uiState.collectAsState()
        LaunchedEffect(birdsViewModel) {
            birdsViewModel.updateImages()
        }
        BirdsPage(
            uiState,
            onCategorySelect = {
                birdsViewModel.selectCategory(it)
            },
            onBirdSelect = {
                navigator.push(BirdsDetailsScreen())
            }
        )
    }
}
