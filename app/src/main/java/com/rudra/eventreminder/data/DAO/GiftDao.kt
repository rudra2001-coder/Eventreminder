package com.rudra.eventreminder.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rudra.eventreminder.data.Gift
import kotlinx.coroutines.flow.Flow

@Dao
interface GiftDao {
    @Query("SELECT * FROM gifts WHERE eventId = :eventId")
    fun getGiftsForEvent(eventId: Int): Flow<List<Gift>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGift(gift: Gift)

    @Update
    suspend fun updateGift(gift: Gift)

    @Delete
    suspend fun deleteGift(gift: Gift)
}
