package psbmarket.uikit.components.toolbar

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlin.math.abs

@Immutable
public class ToolbarScrollBehavior(
    public val state: ToolbarScrollState,
    public val flingAnimationSpec: DecayAnimationSpec<Float>?,
) {
    public val nestedScrollConnection: NestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            if (available.y > 0f) return Offset.Zero

            val prevHeightOffset = state.heightOffset
            state.heightOffset += available.y

            return when {
                prevHeightOffset != state.heightOffset -> available.copy(x = 0f)
                else -> Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            state.contentOffset += consumed.y

            if (available.y < 0f || consumed.y < 0f) {
                val oldHeightOffset = state.heightOffset

                state.heightOffset += consumed.y

                return Offset(0f, state.heightOffset - oldHeightOffset)
            }

            if (consumed.y == 0f && available.y > 0) {
                state.contentOffset = 0f
            }

            if (available.y > 0f) {
                val oldHeightOffset = state.heightOffset

                state.heightOffset += available.y

                return Offset(0f, state.heightOffset - oldHeightOffset)
            }

            return Offset.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            var result = super.onPostFling(consumed, available)

            if (state.collapsedFraction > 0.01f && state.collapsedFraction < 1f) {
                result += flingToolbar(
                    state = state,
                    initialVelocity = available.y,
                    flingAnimationSpec = flingAnimationSpec
                )
                snapToolbar(state)
            }

            return result
        }
    }
}

private suspend fun flingToolbar(
    state: ToolbarScrollState,
    initialVelocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
): Velocity {
    var remainingVelocity = initialVelocity

    if (flingAnimationSpec != null && abs(initialVelocity) > 1f) {
        var lastValue = 0f

        AnimationState(
            initialValue = 0f,
            initialVelocity = initialVelocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset

                state.heightOffset = initialHeightOffset + delta

                val consumed = abs(initialHeightOffset - state.heightOffset)

                lastValue = value
                remainingVelocity = this.velocity

                if (abs(delta - consumed) > 0.5f) {
                    cancelAnimation()
                }
            }
    }

    return Velocity(0f, remainingVelocity)
}

private suspend fun snapToolbar(state: ToolbarScrollState) {
    if (state.heightOffset < 0 && state.heightOffset > state.heightOffsetLimit) {
        AnimationState(
            initialValue = state.heightOffset
        ).animateTo(
            targetValue = if (state.collapsedFraction < 0.5f) 0f else state.heightOffsetLimit,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
        ) {
            state.heightOffset = value
        }
    }
}

@Composable
public fun rememberToolbarScrollBehavior(): ToolbarScrollBehavior {
    return ToolbarScrollBehavior(
        state = rememberToolbarScrollState(
            initialHeightOffsetLimit = -Float.MAX_VALUE
        ),
        flingAnimationSpec = rememberSplineBasedDecay()
    )
}