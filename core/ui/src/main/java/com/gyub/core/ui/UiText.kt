package com.gyub.core.ui

import android.content.Context
import android.net.http.HttpException
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gyub.core.model.NetworkError
import com.gyub.core.model.RootError
import com.gyub.core.ui.UiText.StringResource
import java.io.IOException

/**
 * 공통 Ui 문자열 관리 클래스
 *
 * @author   Gyub
 * @created  2024/09/07
 */
sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any,
    ) : UiText()

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringResource -> stringResource(resId, *args)
    }

    fun asString(context: Context): String = when (this) {
        is DynamicString -> value
        is StringResource -> context.getString(resId, *args)
    }
}

fun RootError.toUiText(): UiText {
    return when (this) {
        is NetworkError -> StringResource(R.string.core_ui_error_message_network)
        is RootError.UNKNOWN -> StringResource(R.string.core_ui_error_message_unknown)
    }
}