package com.gyub.core.common.extensions

import java.util.Locale

/**
 * Double 형에 대한 확장 함수
 *
 * @author   Gyub
 * @created  2024/08/06
 */

/**
 * 소수점 첫째 자리까지의 문자열 반환
 *
 * @return 소수점 첫째 자리까지의 문자열
 */
fun Double?.formatToSingleDecimal(): String {
    this ?: return "0"
    return String.format(Locale.US, "%.1f", this)
}