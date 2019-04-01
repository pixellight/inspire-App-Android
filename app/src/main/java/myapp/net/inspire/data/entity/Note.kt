package myapp.net.inspire.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Alucard on 2/18/2019.
 */
@Entity(tableName = "note_tbl")
data class Note(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "title")
        var title: String? = "",
        @ColumnInfo(name = "body")
        var body: String? = "",
        @ColumnInfo(name = "type")
        var type: Int? = 0,
        @ColumnInfo(name = "created_date")
        var createdDate: String? = "",
        @ColumnInfo(name = "modified_date")
        var modifiedDate: String? = ""
):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readValue(Long::class.java.classLoader) as? Long,
                parcel.readString(),
                parcel.readString(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeValue(id)
                parcel.writeString(title)
                parcel.writeString(body)
                parcel.writeValue(type)
                parcel.writeString(createdDate)
                parcel.writeString(modifiedDate)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Note> {
                override fun createFromParcel(parcel: Parcel): Note {
                        return Note(parcel)
                }

                override fun newArray(size: Int): Array<Note?> {
                        return arrayOfNulls(size)
                }
        }
}