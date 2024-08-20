package com.gyub.core.common.extensions

/**
 * Int 형 확장함수
 *
 * @author   Gyub
 * @created  2024/08/19
 */

/**
 * [receiver] null 일 시 default Value 반환
 *
 * @param defaultValue
 */
fun Int?.orDefault(defaultValue: Int = 0) = this ?: defaultValue