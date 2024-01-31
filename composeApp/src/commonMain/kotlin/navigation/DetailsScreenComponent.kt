package navigation

import models.BirdImage
import com.arkivanov.decompose.ComponentContext

class DetailsScreenComponent(
    val selectedBird: BirdImage? = null,
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
): ComponentContext by componentContext {

    fun goBack() {
        onGoBack()
    }
}
