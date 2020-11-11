package com.inhyuck.adlist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inhyuck.adlist.db.entity.Ad

@Dao
abstract class AdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(Ads: List<Ad>?)

    @Query("SELECT * FROM ads")
    abstract fun loadAll(): LiveData<List<Ad>>

    @Query("SELECT * FROM ads where appId=:appId")
    abstract fun getAd(appId: String): LiveData<Ad>

    @Query("DELETE FROM ads")
    abstract fun deleteAll()
}