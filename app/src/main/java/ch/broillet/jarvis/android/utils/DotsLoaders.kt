// Thanks to EugeneTheDev : https://gist.github.com/EugeneTheDev/a27664cb7e7899f964348b05883cbccd

package ch.broillet.jarvis.android.utils

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DotsPulsing(dotSize: Dp, numberOfDots: Int, delayUnit: Int, dotColor: Color, spaceBetween: Dp) {

    @Composable
    fun Dot(scale: Float) {
        Spacer(
            Modifier
                .size(dotSize)
                .scale(scale)
                .background(
                    color = dotColor, shape = CircleShape
                )
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateScaleWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = delayUnit * numberOfDots
            0f at delay with LinearEasing
            1f at delay + delayUnit with LinearEasing
            0f at delay + numberOfDots * delayUnit
        })
    )

    val scales = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        scales.add(animateScaleWithDelay(delay = i * delayUnit))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        scales.forEach {
            Dot(it.value)
            Spacer(Modifier.width(spaceBetween))
        }
    }
}


@Composable
fun DotsElastic(dotSize: Dp, numberOfDots: Int, delayUnit: Int, dotColor: Color, spaceBetween: Dp) {
    val minScale = 0.6f

    @Composable
    fun Dot(scale: Float) {
        Spacer(
            Modifier
                .size(dotSize)
                .scale(scaleX = minScale, scaleY = scale)
                .background(
                    color = dotColor, shape = CircleShape
                )
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateScaleWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minScale,
        targetValue = minScale,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = numberOfDots * delayUnit
            minScale at delay with LinearEasing
            1f at delay + delayUnit with LinearEasing
            minScale at delay + numberOfDots * delayUnit
        })
    )

    val scales = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        scales.add(animateScaleWithDelay(delay = i * delayUnit))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        scales.forEach {
            Dot(it.value)
            Spacer(Modifier.width(spaceBetween))
        }
    }
}

@Composable
fun DotsFlashing(
    dotSize: Dp, numberOfDots: Int, delayUnit: Int, dotColor: Color, spaceBetween: Dp
) {
    val minAlpha = 0.1f

    @Composable
    fun Dot(alpha: Float) = Spacer(
        Modifier
            .size(dotSize)
            .alpha(alpha)
            .background(
                color = dotColor, shape = CircleShape
            )
    )

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = numberOfDots * delayUnit
            minAlpha at delay with LinearEasing
            1f at delay + delayUnit with LinearEasing
            minAlpha at delay + numberOfDots * delayUnit
        })
    )

    val alphas = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        alphas.add(animateAlphaWithDelay(delay = i * delayUnit))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        alphas.forEach {
            Dot(it.value)
            Spacer(Modifier.width(spaceBetween))
        }
    }
}

@Composable
fun DotsTyping(dotSize: Dp, numberOfDots: Int, delayUnit: Int, dotColor: Color, spaceBetween: Dp) {
    val maxOffset = (numberOfDots * 2).toFloat()

    @Composable
    fun Dot(offset: Float) {
        Spacer(
            Modifier
                .size(dotSize)
                .offset(y = -offset.dp)
                .background(
                    color = dotColor, shape = CircleShape
                )
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    @Composable
    fun animateOffsetWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = numberOfDots * delayUnit
            0f at delay with LinearEasing
            maxOffset at delay + delayUnit with LinearEasing
            0f at delay + (numberOfDots * delayUnit / 2)
        })
    )

    val offsets = arrayListOf<State<Float>>()
    for (i in 0 until numberOfDots) {
        offsets.add(animateOffsetWithDelay(delay = i * delayUnit))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = maxOffset.dp)
    ) {
        offsets.forEach {
            Dot(it.value)
            Spacer(Modifier.width(spaceBetween))
        }
    }
}

@Composable
fun DotsCollision(
    dotSize: Dp, numberOfDots: Int, delayUnit: Int, dotColor: Color, spaceBetween: Dp
) {
    val maxOffset = 30f
    val delayUnit = 500

    @Composable
    fun Dot(offset: Float) {
        Spacer(
            Modifier
                .size(dotSize)
                .offset(x = offset.dp)
                .background(
                    color = dotColor, shape = CircleShape
                )
        )
    }

    val infiniteTransition = rememberInfiniteTransition()

    val offsetLeft by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = delayUnit * 3
            0f at 0 with LinearEasing
            -maxOffset at delayUnit / 2 with LinearEasing
            0f at delayUnit
        })
    )
    val offsetRight by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            durationMillis = delayUnit * 3
            0f at delayUnit with LinearEasing
            maxOffset at delayUnit + delayUnit / 2 with LinearEasing
            0f at delayUnit * 2
        })
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = maxOffset.dp)
    ) {
        Dot(offsetLeft)
        Spacer(Modifier.width(spaceBetween))
        for (i in 0 until numberOfDots - 2) {
            Dot(0f)
            Spacer(Modifier.width(spaceBetween))
        }
        Dot(offsetRight)
    }
}