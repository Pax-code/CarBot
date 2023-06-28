package com.example.carbot.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single


@Dao
interface DBDao {

    @Query("SELECT * FROM DBModel")
    fun getAll(): Flowable<List<DBModel>>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Electric'")
    fun getElectricTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Water'")
    fun getWaterTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Gas'")
    fun getGassTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Coal'")
    fun getCoalTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Paper'")
    fun getPaperTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Metal'")
    fun getMetalTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Battery'")
    fun getBatteryTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Glass'")
    fun getGlassTotalValue(): Flowable<Float>

    @Query("SELECT SUM(value) FROM DBModel WHERE resourceType = 'Plastic'")
    fun getPlasticTotalValue(): Flowable<Float>

    @Insert
    fun insert(dbModel: DBModel) : Completable
}