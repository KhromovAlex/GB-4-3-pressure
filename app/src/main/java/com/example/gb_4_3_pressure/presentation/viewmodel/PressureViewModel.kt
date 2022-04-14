package com.example.gb_4_3_pressure.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gb_4_3_pressure.domain.entity.PressureInfo
import com.example.gb_4_3_pressure.domain.repository.PressureRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import kotlin.random.Random

class PressureViewModel(
    val pressureRepository: PressureRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _liveData = MutableLiveData<List<PressureInfo>>()
    val liveData: LiveData<List<PressureInfo>> = _liveData
    private val randomTop = Random(120)
    private val randomLower = Random(80)
    private val randomPulse = Random(90)

    fun watchLessons() {
        disposables += pressureRepository.watchPressure()
            .subscribeOn(Schedulers.io())
            .subscribe {
                _liveData.postValue(it)
            }
    }

    fun sendNewPressure() {
        val currentDate = Calendar.getInstance().time
        val pressure = PressureInfo(
            id = currentDate.time.toString(),
            topPressure = randomTop.nextInt(100, 160),
            lowerPressure = randomLower.nextInt(50, 120),
            pulse = randomPulse.nextInt(40,120),
            date = currentDate,
        )
        pressureRepository.sendNewPressure(pressure)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}
