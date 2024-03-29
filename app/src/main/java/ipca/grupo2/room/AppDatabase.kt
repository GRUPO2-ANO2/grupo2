package ipca.grupo2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ipca.grupo2.room.dao.EventoDAO
import ipca.grupo2.room.dao.LeituraDAO
import ipca.grupo2.room.dao.UtilizadorDAO
import ipca.grupo2.room.entities.EventoEntity
import ipca.grupo2.room.entities.LeituraEntity
import ipca.grupo2.room.entities.UtilizadorEntity

@Database(entities = [UtilizadorEntity::class, EventoEntity::class, LeituraEntity::class], version = 9)
@TypeConverters(Converters::class)
abstract class  AppDatabase : RoomDatabase()  {
    // Signatures for frontend access to room
    abstract fun utilizadorDao() : UtilizadorDAO
    abstract fun eventoDao() : EventoDAO
    abstract fun leituraDao() : LeituraDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "db_grupo2"
                        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE
        }
    }
}