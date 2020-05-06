package com.devmmurray.flickrrocket.data.model

import android.os.Parcel
import android.os.Parcelable

class PhotoObject(
    val title: String?,
    val link: String?,
    var isFavorite: Boolean = false,
    var comment: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(link)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoObject> {
        override fun createFromParcel(parcel: Parcel): PhotoObject {
            return PhotoObject(parcel)
        }

        override fun newArray(size: Int): Array<PhotoObject?> {
            return arrayOfNulls(size)
        }
    }
}