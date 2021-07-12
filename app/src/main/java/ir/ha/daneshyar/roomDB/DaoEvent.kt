package ir.ha.daneshyar.roomDB

import androidx.room.*
import ir.ha.daneshyar.models.EventModel

@Dao
interface DaoEvent {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insert(event : EventModel) : Long

    @Delete
    fun delete(event: EventModel)

    @Update
    fun update(event: EventModel)

    @Query("SELECT * FROM EventModel;")
    fun allEvents() : List<EventModel>

    @Query("SELECT * FROM EventModel WHERE  id =:uid;")
    fun searchById(uid : Long) :  List<EventModel>

    @Query("Delete From EventModel")
    fun deleteAll()


}