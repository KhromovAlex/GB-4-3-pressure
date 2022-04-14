package com.example.gb_4_3_pressure.domain.repository

import com.example.gb_4_3_pressure.domain.entity.PressureInfo
import io.reactivex.rxjava3.subjects.BehaviorSubject

interface PressureRepository {
    fun sendNewPressure(pressure: PressureInfo)
    fun watchPressure(): BehaviorSubject<List<PressureInfo>>
}
