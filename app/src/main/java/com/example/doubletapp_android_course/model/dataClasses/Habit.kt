package com.example.doubletapp_android_course.model.dataClasses

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.doubletapp_android_course.model.enums.HabitType

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val priority: String,
    val type: HabitType,
    val count: Int,
    val frequency: Int,
    val color: Int?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        HabitType.entries[parcel.readInt()], // <- restore from ordinal
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(priority)
        parcel.writeInt(type.ordinal) // <- save ordinal instead of parcelable
        parcel.writeInt(count)
        parcel.writeInt(frequency)
        parcel.writeValue(color)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit = Habit(parcel)
        override fun newArray(size: Int): Array<Habit?> = arrayOfNulls(size)
    }
}