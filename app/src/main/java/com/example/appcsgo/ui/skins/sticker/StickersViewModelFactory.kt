package com.example.appcsgo.ui.skins.sticker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appcsgo.data.api.RetrofitClient
import com.example.appcsgo.data.repository.StickersRepository

class StickersViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repo = StickersRepository(RetrofitClient.apiService)
        @Suppress("UNCHECKED_CAST")
        return StickersViewModel(repo) as T
    }
}