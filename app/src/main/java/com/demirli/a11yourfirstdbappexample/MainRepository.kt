package com.demirli.a11yourfirstdbappexample

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.demirli.a11yourfirstdbappexample.data.ExampleDao
import com.demirli.a11yourfirstdbappexample.data.ExampleDb
import com.demirli.a11yourfirstdbappexample.model.Example

class MainRepository(context: Context) {
    private val db by lazy { ExampleDb.getInstance(context) }
    private val dao by lazy {db.exampleDao()}

    fun getExample(): LiveData<List<Example>> = dao.getExample()

    fun deleteAll() {
        DeleteAsyncTask(dao).execute()
    }

    private class DeleteAsyncTask(val dao: ExampleDao): AsyncTask<Void,Void,Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            dao.deleteAll()
            return null
        }
    }
}