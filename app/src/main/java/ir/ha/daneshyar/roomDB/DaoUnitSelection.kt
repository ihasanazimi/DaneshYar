package ir.ha.daneshyar.roomDB

import androidx.room.*
import ir.ha.daneshyar.models.UnitModel

@Dao
interface DaoUnitSelection {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insert(unit : UnitModel) : Long

    @Delete
    fun delete(unit: UnitModel)

    @Update
    fun update(unit: UnitModel)

    @Query("SELECT * FROM UnitModel;")
    fun allUnits() : List<UnitModel>

    @Query("SELECT * FROM UnitModel WHERE  numberOfWeek =:numberOfWeek ORDER BY numberOfWeek ASC;")
    fun searchById(numberOfWeek : Long) :  List<UnitModel>

    @Query("Delete From UnitModel")
    fun deleteAll()


}