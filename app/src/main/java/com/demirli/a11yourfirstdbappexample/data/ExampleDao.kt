package com.demirli.a11yourfirstdbappexample.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demirli.a11yourfirstdbappexample.model.Example

@Dao
interface ExampleDao {

    @Query("SELECT * FROM exampledata")
    fun getExample(): LiveData<List<Example>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addExample(example: Example)

    @Query("DELETE FROM exampledata")
    fun deleteAll()
}
