package ir.formol.roomDB

import androidx.room.*
import ir.formol.models.DayModel
import ir.formol.models.EventModel

@Dao
interface DaoDay {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insert(day : DayModel) : Long

    @Delete
    fun delete(day: DayModel)

    @Update
    fun update(day: DayModel)

    @Query("SELECT * FROM DayModel;")
    fun allDays() : List<DayModel>

    @Query("SELECT * FROM DayModel WHERE  numberOfWeek =:numberOffWeek;")
    fun searchById(numberOffWeek : Long) :  List<DayModel>

    @Query("Delete From DayModel")
    fun deleteAll()


}