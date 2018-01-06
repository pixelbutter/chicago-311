package com.chicago311.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.chicago311.data.model.CityService

@Dao
interface ServicesDao {

    // todo group by type?

    @Query("SELECT * FROM services")
    fun getAllServices(): LiveData<List<CityService>>

    @Query("SELECT * FROM services WHERE service_code = :serviceCode")
    fun getServiceSummary(serviceCode: String): LiveData<CityService>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertServices(cityServices: List<CityService>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun createServiceIfNotExists(cityService: CityService)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertService(cityService: CityService)
}