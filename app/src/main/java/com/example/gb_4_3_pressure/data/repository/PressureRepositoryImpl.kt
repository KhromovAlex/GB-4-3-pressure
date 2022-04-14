package com.example.gb_4_3_pressure.data.repository

import com.example.gb_4_3_pressure.domain.entity.PressureInfo
import com.example.gb_4_3_pressure.domain.repository.PressureRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.subjects.BehaviorSubject

class PressureRepositoryImpl : PressureRepository {
    private val db = Firebase.firestore
    private val stream: BehaviorSubject<List<PressureInfo>> =
        BehaviorSubject.createDefault(listOf())

    init {
        db.collection("pressure")
            .get()
            .addOnSuccessListener { result ->
                val list = mutableListOf<PressureInfo>()
                for (document in result) {
                    val data = parse(document.data)
                    data?.let {
                        list.add(it)
                    }
                }
                stream.onNext(list)
            }
    }

    override fun sendNewPressure(pressure: PressureInfo) {
        db.collection("pressure")
            .add(pressure)
            .addOnSuccessListener {
                val list = mutableListOf<PressureInfo>()
                stream.value?.let {
                    list.addAll(it)
                }
                list.add(pressure)
                stream.onNext(list)
            }
    }

    override fun watchPressure(): BehaviorSubject<List<PressureInfo>> = stream

    private fun parse(json: Map<String, Any>): PressureInfo? {
        val id = json.getOrDefault("id", null) as? String?
        val date = json.getOrDefault("date", null) as? Timestamp?
        val lowerPressure = json.getOrDefault("lowerPressure", null) as? Long?
        val topPressure = json.getOrDefault("topPressure", null) as? Long?
        val pulse = json.getOrDefault("pulse", null) as? Long?

        return if (id != null && date != null && lowerPressure != null && topPressure != null && pulse != null) {
            PressureInfo(
                id = id,
                date = date.toDate(),
                lowerPressure = lowerPressure.toInt(),
                topPressure = topPressure.toInt(),
                pulse = pulse.toInt()
            )
        } else {
            null
        }
    }
}
