package com.example.gb_4_3_pressure.domain.entity

import java.util.*

data class PressureInfo(
    val id: String,
    val topPressure: Int,
    val lowerPressure: Int,
    val pulse: Int,
    val date: Date
)
