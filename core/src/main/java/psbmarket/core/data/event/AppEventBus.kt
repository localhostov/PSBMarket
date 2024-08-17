package psbmarket.core.data.event

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

public val LocalAppEventBus: ProvidableCompositionLocal<AppEventBus> = staticCompositionLocalOf {
    error("no default LocalAppEventBus provided")
}

public class AppEventBus {
    private val eventFlow = MutableSharedFlow<AppEvent>()

    public fun subscribe(
        scope: CoroutineScope,
        block: suspend (AppEvent) -> Unit
    ): Job = eventFlow.onEach(block).launchIn(scope)

    public suspend fun emit(appEvent: AppEvent): Unit = eventFlow.emit(appEvent)
}