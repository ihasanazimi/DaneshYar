package ir.ha.daneshyar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity
class DayModel(){

    @PrimaryKey
    @ColumnInfo
    var uId : Long ? = null
    @ColumnInfo
    var numberOfWeek : Long ? = null
    @ColumnInfo
    var dayTitle : String? = null
    @ColumnInfo
    var selectionState : Boolean = false

    constructor(numberOfWeek : Long , dayTitle : String , selectionState : Boolean ) : this(){
        this.numberOfWeek = numberOfWeek
        this.dayTitle = dayTitle
        this.selectionState = selectionState
    }

    constructor(dayTitle : String , selectionState : Boolean ) : this(){
        this.dayTitle = dayTitle
        this.selectionState = selectionState
    }

}