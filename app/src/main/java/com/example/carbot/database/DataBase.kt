package com.example.carbot.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database ( entities = [DBModel::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun dbdao(): DBDao
}