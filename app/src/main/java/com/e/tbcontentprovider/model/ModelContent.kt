package com.e.tbcontentprovider.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tb_color")
data class ModelContent(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk: Int = -1,

    @ColumnInfo(name = "color")
    var color: String = ""

) : Parcelable {
    override fun toString(): String {
        return "ModelContent(pk=$pk, color='$color')"
    }
}