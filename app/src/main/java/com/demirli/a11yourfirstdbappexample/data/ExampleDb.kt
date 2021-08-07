package com.demirli.a11yourfirstdbappexample.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.demirli.a11yourfirstdbappexample.model.Example

@Database(entities = arrayOf(Example::class), version = 1)
abstract class ExampleDb(): RoomDatabase() {
    abstract fun exampleDao(): ExampleDao

    companion object{

        var dbName = ""

        var INSTANCE: ExampleDb? = null

        fun getInstance(context: Context): ExampleDb {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ExampleDb::class.java,
                    dbName
                )
                    .addCallback(roomDbCallback)
                    .enableMultiInstanceInvalidation()
                    .build()
            }
            return INSTANCE as ExampleDb
        }

        private val roomDbCallback = object: RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateAsyncTask(INSTANCE).execute()
            }
        }

        class PopulateAsyncTask(private val db: ExampleDb?): AsyncTask<Void, Void, Void>() {
            private val dao: ExampleDao? by lazy { db?.exampleDao() }
            override fun doInBackground(vararg params: Void?): Void? {

                var example = Example(
                    444,"Bill","bill@company.com"
                )
                dao?.addExample(example)

                example = Example(
                    555, "Donna","donna@home.org"
                )
                dao?.addExample(example)

                example = Example(
                    666, "Emir","emirdemirli@gmail.com"
                )
                dao?.addExample(example)
                return null
            }
        }
    }
}