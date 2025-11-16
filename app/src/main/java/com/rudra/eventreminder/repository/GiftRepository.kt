package com.rudra.eventreminder.repository

import com.rudra.eventreminder.data.DAO.GiftDao
import com.rudra.eventreminder.data.Gift
import kotlinx.coroutines.flow.Flow

class GiftRepository(private val giftDao: GiftDao) {
    fun getGiftsForEvent(eventId: Int): Flow<List<Gift>> {
        return giftDao.getGiftsForEvent(eventId)
    }

    suspend fun addGift(gift: Gift) {
        giftDao.addGift(gift)
    }

    suspend fun updateGift(gift: Gift) {
        giftDao.updateGift(gift)
    }

    suspend fun deleteGift(gift: Gift) {
        giftDao.deleteGift(gift)
    }
}
