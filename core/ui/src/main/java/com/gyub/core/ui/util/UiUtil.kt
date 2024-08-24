package com.gyub.core.ui.util

import android.content.Context
import com.gyub.core.ui.R
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

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

/**
 * 영화 배포 날짜가 오늘 날짜 이전인지 판단
 *
 * @param releaseDate
 * @return 개봉 여부
 */
fun isDateTodayOrBefore(releaseDate: String): Boolean {
    val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

    return try {
        val releaseLocalDate: LocalDate = LocalDate.parse(releaseDate)
        releaseLocalDate <= today
    } catch (e: Exception) {
        false
    }
}