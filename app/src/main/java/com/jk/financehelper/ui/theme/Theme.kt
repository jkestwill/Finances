package com.jk.financehelper.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Celadon,
    secondary = SeaGreen,
    tertiary = BrownPurple,
    background = LightCreamy,

    )

private val LightColorScheme = lightColorScheme(
    primary = Celadon,
    secondary = TeaGreen,
    onSecondary = LightPink,
    tertiary = BrownPurple,
    background = LightCreamy,

    )


data class FinanceHelperColors(
    val primaryText: Color,
    val secondaryText: Color,
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val buttonDeleteColor:Color,
    val defaultButtonColor:Color,
    val error: Color
)

data class FinanceHelperShape(
    val padding: Dp,
    val shape10: Shape,
    val shape20: Shape,
    val shape30: Shape,
)

data class FinanceHelperTypography(
    val label: TextStyle,
    val body: TextStyle,
    val h1:TextStyle,
    val h2:TextStyle,
    val h3:TextStyle,
    val h4:TextStyle,
)

enum class FinanceHelperSize {
    SMALL, MEDIUM, LARGE
}

object FinanceHelperTheme {
    val colors: FinanceHelperColors
        @Composable
        get() = LocalFinanceHelperColors.current
    val shape: FinanceHelperShape
        @Composable
        get() = LocalFinanceHelperShape.current
    val typography: FinanceHelperTypography
        @Composable
        get() = LocalFinanceHelperTypography.current
}

val LocalFinanceHelperColors = staticCompositionLocalOf<FinanceHelperColors> {
    error("No color provided")
}
val LocalFinanceHelperShape = staticCompositionLocalOf<FinanceHelperShape> {
    error("No shape provided")
}
val LocalFinanceHelperTypography = staticCompositionLocalOf<FinanceHelperTypography> {
    error("No typography provided")
}


@Composable
fun FinanceHelperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    textSize: FinanceHelperSize = FinanceHelperSize.MEDIUM,
    paddingSize: FinanceHelperSize = FinanceHelperSize.MEDIUM,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colors = lightPalette
    val shape = FinanceHelperShape(
        padding = when (paddingSize) {
            FinanceHelperSize.MEDIUM -> 10.dp
            FinanceHelperSize.LARGE -> 15.dp
            FinanceHelperSize.SMALL -> 4.dp
            else -> {
                error("No such size $paddingSize")
            }
        },
        shape10 = RoundedCornerShape(10),
        shape20 = RoundedCornerShape(20),
        shape30 = RoundedCornerShape(30),
    )
    val typography = FinanceHelperTypography(
        label = TextStyle(
            fontSize = when(textSize){
                FinanceHelperSize.LARGE->24.sp
                FinanceHelperSize.MEDIUM->20.sp
                FinanceHelperSize.SMALL-> 16.sp
                else-> error("No such typography style $textSize")
            },
            fontWeight = FontWeight.SemiBold,
            color = MidnightGreen
        ),
        body =TextStyle(
            fontSize = when(textSize){
                FinanceHelperSize.LARGE->20.sp
                FinanceHelperSize.MEDIUM->16.sp
                FinanceHelperSize.SMALL-> 12.sp
                else-> error("No such typography style $textSize")
            },
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        ),
        h1 = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 24.sp),
        h2 = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black,fontSize = 20.sp),
        h3 = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black,fontSize = 16.sp),
        h4 = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black,fontSize = 12.sp),
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primaryBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    CompositionLocalProvider(
        LocalFinanceHelperColors provides colors,
        LocalFinanceHelperShape provides shape,
        LocalFinanceHelperTypography provides typography,
        content=content
    )
}