package navigation

import models.BirdImage

sealed interface ListScreenEvent {
    data class SelectBirdCategory(val selectedCategory: String): ListScreenEvent
    data class SelectBird(val bird: BirdImage): ListScreenEvent
}