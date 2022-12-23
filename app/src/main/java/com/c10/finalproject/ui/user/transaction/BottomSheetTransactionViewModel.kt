package com.c10.finalproject.ui.user.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c10.finalproject.data.local.datastore.DataStoreManager
import com.c10.finalproject.data.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetTransactionViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {
    fun addOrder(token: String, id: Int) =
        viewModelScope.launch(Dispatchers.IO) { orderRepository.addOrder(token, id) }

    fun getToken() = dataStoreManager.getToken.asLiveData()
}