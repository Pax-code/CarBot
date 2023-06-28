package com.example.carbot.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class DBModel(

    @ColumnInfo(name = "value")
    var value: Float,

    @ColumnInfo(name = "resourceType")
    var resourceType: String
              ) {

    @PrimaryKey(autoGenerate = true)
    var id = 0
}