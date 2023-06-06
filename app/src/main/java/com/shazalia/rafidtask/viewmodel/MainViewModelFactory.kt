package com.shazalia.rafidtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shazalia.rafidtask.repository.Repository

/**
 * @Author: shazawdidi
 * @Date: 6/2/2023
 */
class MainViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}