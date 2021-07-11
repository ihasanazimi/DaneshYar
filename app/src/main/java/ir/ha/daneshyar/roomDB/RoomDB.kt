package ir.formol.roomDB

import android.content.Context
import androidx.room.*
import ir.formol.models.DayModel
import ir.formol.models.SessionModel
import ir.formol.models.EventModel
import ir.formol.models.UnitModel

@Database(
    entities = [EventModel::class , SessionModel::class , UnitModel::class , DayModel::class ], version = 5, exportSchema = false
)
abstract class RoomDB : RoomDatabase() {

    //Dao
    abstract fun daoEvent(): DaoEvent
    abstract fun daoSession(): DaoSession
    abstract fun daoUnitSelection(): DaoUnitSelection
    abstract fun daoDay(): DaoDay


    companion object {

        @Volatile
        var database: RoomDB? = null

        // singleTon design pattern

        fun getDataBase(context: Context): RoomDB {
            val tempInstance = database
            if (database != null) {
                return tempInstance as RoomDB
            }

            //synchronized  --- means -->  access just one there on main thread!

            synchronized(this) {

                val instance =
                    Room.databaseBuilder(context.applicationContext, RoomDB::class.java, "database")
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build()

                database = instance
                return instance
            }
        }
    }
}