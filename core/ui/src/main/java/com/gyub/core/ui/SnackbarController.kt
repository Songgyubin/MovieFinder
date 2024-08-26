package com.gyub.core.ui

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import java.net.UnknownHostException

/**
 * 화면 전역에서 사용할 수 있는 스낵바 컨트롤러
 *
 * @author   Gyub
 * @created  2024/08/22
 */

sealed interface SnackbarEvent {
    val action: SnackbarAction?

    data class Message(
        val message: String,
        override val action: SnackbarAction? = null,
    ) : SnackbarEvent

    data class MessageId(
        val messageId: Int,
        override val action: SnackbarAction? = null,
    ) : SnackbarEvent
}

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit,
)

object SnackbarController {

    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent.Message) {
        _events.send(event)
    }

    suspend fun sendEvent(messageId: Int) {
        _events.send(SnackbarEvent.MessageId(messageId))
    }

    suspend fun sendEvent(throwable: Throwable?) {
        throwable ?: return

        val messageId: Int = when (throwable) {
            is UnknownHostException -> R.string.core_ui_error_message_network
            else -> R.string.core_ui_error_message_unknown
        }
        _events.send(SnackbarEvent.MessageId(messageId))
    }
}