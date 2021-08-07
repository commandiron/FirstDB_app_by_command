package com.demirli.a11yourfirstdbappexample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exampledata")
data class Example(

    @PrimaryKey
    var userid: Int,

    var name: String,

    var email: String

)