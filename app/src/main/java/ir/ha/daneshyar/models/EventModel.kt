package ir.formol.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class EventModel() {

    @PrimaryKey
    @ColumnInfo
    var id : Long? = null
    @ColumnInfo
    var subject : String? = null
    @ColumnInfo
    var des : String? = null
    @ColumnInfo
    var dateTime : String? = null


    constructor(id : Long , subject : String,des : String , dateTime : String) : this(){
        this.id = id
        this.subject = subject
        this.des = des
    }

    constructor(subject : String, des : String , dateTime : String) : this(){
        this.subject = subject
        this.des = des
    }

}