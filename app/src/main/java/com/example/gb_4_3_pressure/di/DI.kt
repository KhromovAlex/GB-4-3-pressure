package com.example.gb_4_3_pressure.di

import com.example.gb_4_3_pressure.data.repository.PressureRepositoryImpl
import com.example.gb_4_3_pressure.domain.repository.PressureRepository
import com.example.gb_4_3_pressure.presentation.viewmodel.PressureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DI {
    fun getModule() = module {

        single<PressureRepository> {
            PressureRepositoryImpl()
        }

        viewModel { PressureViewModel(pressureRepository = get()) }
    }
}
