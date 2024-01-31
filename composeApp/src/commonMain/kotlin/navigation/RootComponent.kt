package navigation

import models.BirdImage
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext
): ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()

    val childStack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.ListScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Child {
        return when(config) {
            Configuration.ListScreen -> Child.ListScreen(
                ListScreenComponent(
                    componentContext = context,
                    onBirdClicked = { bird ->
                        navigation.pushNew(Configuration.DetailsScreen(bird))
                    }
                )
            )
            is Configuration.DetailsScreen -> Child.DetailsScreen(
                DetailsScreenComponent(
                    selectedBird = config.bird,
                    componentContext = context,
                    onGoBack = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    sealed class Child {
        data class ListScreen(val component: ListScreenComponent): Child()
        data class DetailsScreen(val component: DetailsScreenComponent): Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object ListScreen: Configuration()

        @Serializable
        data class DetailsScreen(val bird: BirdImage): Configuration()
    }
}
