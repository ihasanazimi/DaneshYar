package ir.ha.daneshyar.roomDB

import androidx.room.*
import ir.ha.daneshyar.models.SessionModel

@Dao
interface DaoSession {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insert(session : SessionModel) : Long

    @Delete
    fun delete(session: SessionModel)

    @Update
    fun update(session: SessionModel)

    @Query("SELECT * FROM SessionModel;")
    fun allSessions() : List<SessionModel>

    @Query("SELECT * FROM SessionModel WHERE  id =:uid;")
    fun searchById(uid : Long) :  List<SessionModel>

    @Query("Delete From SessionModel")
    fun deleteAll()


}