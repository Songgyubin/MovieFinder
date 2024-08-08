package com.gyub.build_logic

import com.gyub.build_logic.base.androidExtension
import org.gradle.api.Project

/**
 * name space 관련 함수 정의
 *
 * @author   Gyub
 * @created  2024/08/08
 *
 */

fun Project.setNameSpace(name:String) {
    androidExtension.apply {
        namespace = "com.gyub.moviefinder.$name"
    }
}