package ir.formol.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UnitModel() {

    @PrimaryKey
    @ColumnInfo
    var uId: Long? = null
    @ColumnInfo
    var unit: Short? = null
    @ColumnInfo
    var sessionCode: String? = null
    @ColumnInfo
    var sessionName: String? = null
    @ColumnInfo
    var sessionClass: String? = null
    @ColumnInfo
    var teacherName: String? = null
    @ColumnInfo
    var sHour: Int? = null
    @ColumnInfo
    var sMinute: Int? = null
    @ColumnInfo
    var eHour: Int? = null
    @ColumnInfo
    var eMinute: Int? = null
    @ColumnInfo
    var numberOfWeek : Int? = null

    constructor(
        uId: Long,
        unit: Short,
        sessionCode: String,
        sessionName: String,
        sessionClass: String,
        teacherName: String,
        sHour: Int,
        sMinute: Int,
        eHour: Int,
        eMinute: Int,
        numberOfWeek : Int
    ) : this() {
        this.uId = uId
        this.unit = unit
        this.sessionCode = sessionCode
        this.sessionName = sessionName
        this.sessionClass = sessionClass
        this.teacherName = teacherName
        this.sHour = sHour
        this.sMinute = sMinute
        this.eHour = eHour
        this.eMinute = eMinute
        this.numberOfWeek = numberOfWeek
    }


    constructor(
        unit: Short,
        sessionCode: String,
        sessionName: String,
        sessionClass: String,
        teacherName: String,
        sHour: Int,
        sMinute: Int,
        eHour: Int,
        eMinute: Int,
        numberOfWeek : Int
    ) : this() {
        this.unit = unit
        this.sessionCode = sessionCode
        this.sessionName = sessionName
        this.sessionClass = sessionClass
        this.teacherName = teacherName
        this.sHour = sHour
        this.sMinute = sMinute
        this.eHour = eHour
        this.eMinute = eMinute
        this.numberOfWeek = numberOfWeek
    }


}