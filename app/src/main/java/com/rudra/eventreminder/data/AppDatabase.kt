package com.rudra.eventreminder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rudra.eventreminder.data.DAO.EventDao
import com.rudra.eventreminder.data.DAO.GiftDao
import com.rudra.eventreminder.data.DAO.HabitDao
import com.rudra.eventreminder.data.DAO.MemoryDao
import com.rudra.eventreminder.data.DAO.NoteDao

@Database(entities = [Event::class, Gift::class, Note::class, Habit::class, Memory::class], version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun giftDao(): GiftDao
    abstract fun noteDao(): NoteDao
    abstract fun habitDao(): HabitDao
    abstract fun memoryDao(): MemoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "event_reminder_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
