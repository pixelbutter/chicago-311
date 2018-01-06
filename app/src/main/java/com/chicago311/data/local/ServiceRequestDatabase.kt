package com.chicago311.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.chicago311.data.model.CityService

@Database(entities = arrayOf(CityService::class), version = 1)
abstract class ServiceRequestDatabase : RoomDatabase() {

    abstract fun servicesDao(): ServicesDao
}