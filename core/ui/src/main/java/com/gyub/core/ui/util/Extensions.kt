package com.gyub.core.ui.util

import android.content.Context
import com.gyub.core.ui.R

/**
 * ui 관련 확장함수
 *
 * @author   Gyub
 * @created  2024/08/24
 */

/**
 * Int의 값을 시간으로 반환
 * 128 -> 2시간 8분
 *
 * @return
 */
fun Int.toHourMinuteString(context: Context): String {
    val hours = this / 60
    val minutes = this % 60
    return context.getString(R.string.core_ui_hour_min, hours, minutes)
}