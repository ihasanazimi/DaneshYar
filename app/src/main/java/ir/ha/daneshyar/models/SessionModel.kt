package ir.ha.daneshyar.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class SessionModel () {

    @PrimaryKey
    @ColumnInfo
    var id : Long? = null
    @ColumnInfo
    var unit : Short? =null
    @ColumnInfo
    var name : String? =null
    @ColumnInfo
    var score : Float? =null


    constructor(unit : Short , name : String , score : Float) : this(){
        this.unit = unit
        this.name = name
        this.score = score
    }
}