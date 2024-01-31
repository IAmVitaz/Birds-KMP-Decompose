package navigation

import models.BirdImage
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class ListScreenComponent(
    val componentContext: ComponentContext,
    private val onBirdClicked: (BirdImage) -> Unit
): ComponentContext by componentContext {
    private val _uiState = MutableValue(BirdsUiState())
    val uiState: Value<BirdsUiState> = _uiState

    fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.SelectBird -> {
                onBirdClicked(event.bird)
            }
            is ListScreenEvent.SelectBirdCategory -> {
                this.selectCategory(category = event.selectedCategory)
            }
        }
    }

    private val httpClient = HttpClient() {
        install(ContentNegotiation) {
            json()
        }
    }

    fun updateImages() {
        runBlocking {
            coroutineScope {
                val images = getImages()
                _uiState.update {
                    it.copy(images = images)
                }
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { state ->
            if (state.selectedCategory == category) {
                state.copy(selectedCategory = null)
            } else {
                state.copy(selectedCategory = category)
            }
        }
    }

    private suspend fun getImages(): List<BirdImage> =
        httpClient
            .get("https://sebi.io/demo-image-api/pictures.json")
            .body<List<BirdImage>>()
}

data class BirdsUiState(
    val images: List<BirdImage> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories = images.map { it.category }.toSet()
    val selectedImages = images.filter { it.category == selectedCategory }
}
