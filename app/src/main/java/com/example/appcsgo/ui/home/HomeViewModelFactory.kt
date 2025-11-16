package com.example.appcsgo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcsgo.data.api.RetrofitClient
import com.example.appcsgo.data.repository.CratesRepository
import com.example.appcsgo.data.repository.CsgoRepository
import com.example.appcsgo.data.repository.StickersRepository

class HomeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val apiService = RetrofitClient.apiService

        val cratesRepo = CratesRepository(apiService)
        val csgoRepo = CsgoRepository()
        val stickersRepo = StickersRepository(apiService)

        @Suppress("UNCHECKED_CAST")
        return HomeViewModel(
            cratesRepo,
            csgoRepo,
            stickersRepo
        ) as T
    }
}
