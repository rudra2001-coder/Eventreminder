package com.rudra.eventreminder.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rudra.eventreminder.data.AppDatabase
import com.rudra.eventreminder.data.Gift
import com.rudra.eventreminder.repository.GiftRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GiftViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GiftRepository

    init {
        val giftDao = AppDatabase.getDatabase(application).giftDao()
        repository = GiftRepository(giftDao)
    }

    fun getGiftsForEvent(eventId: Int): Flow<List<Gift>> {
        return repository.getGiftsForEvent(eventId)
    }

    fun addGift(gift: Gift) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGift(gift)
        }
    }

    fun updateGift(gift: Gift) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGift(gift)
        }
    }

    fun deleteGift(gift: Gift) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGift(gift)
        }
    }
}
