package com.example.doubletapp_android_course.model.dataClasses

import android.os.Parcel
import android.os.Parcelable
import com.example.doubletapp_android_course.model.enums.HabitPriority
import com.example.doubletapp_android_course.model.enums.HabitType

data class Habit(
    val uid: String?,
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val count: Int,
    val frequency: Int,
    val color: Int?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        HabitPriority.entries[parcel.readInt()],
        HabitType.entries[parcel.readInt()],
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(priority.ordinal)
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

