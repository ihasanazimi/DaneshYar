package ir.formol.models

import android.os.Parcel
import android.os.Parcelable
import android.service.quicksettings.Tile

class PDFModel() : Parcelable {

    var imageTitle: String? = null
    var pdfImageForOnlyShow: Int? = null

    constructor(parcel: Parcel) : this() {
        imageTitle = parcel.readString()
        pdfImageForOnlyShow = parcel.readValue(Int::class.java.classLoader) as? Int
    }


    constructor(imageTitle: String, pdfImageForShow: Int) : this() {
        this.imageTitle = imageTitle
        this.pdfImageForOnlyShow = pdfImageForShow
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imageTitle)
        parcel.writeValue(pdfImageForOnlyShow)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PDFModel> {
        override fun createFromParcel(parcel: Parcel): PDFModel {
            return PDFModel(parcel)
        }

        override fun newArray(size: Int): Array<PDFModel?> {
            return arrayOfNulls(size)
        }
    }

}