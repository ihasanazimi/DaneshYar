package ir.ha.daneshyar.roomDB

import android.content.Context
import androidx.room.*
import ir.ha.daneshyar.models.DayModel
import ir.ha.daneshyar.models.SessionModel
import ir.ha.daneshyar.models.EventModel
import ir.ha.daneshyar.models.UnitModel

@Database(
    entities = [EventModel::class, SessionModel::class, UnitModel::class, DayModel::class],
    version = 6,
    exportSchema = false
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