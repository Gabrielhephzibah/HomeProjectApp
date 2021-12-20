package com.cherish.homeprojectapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cherish.homeprojectapp.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DataRepository) :  ViewModel() {
    fun getOrganization() = repository.getOrganizations().asLiveData()
}