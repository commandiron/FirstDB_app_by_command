package com.demirli.a11yourfirstdbappexample

import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.demirli.a11yourfirstdbappexample.data.ExampleDb
import com.demirli.a11yourfirstdbappexample.model.Example
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var data: List<Example>

    private var dbList = ArrayList<String>()

    private var firstOrLoadedFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        data = listOf()

        load_create_db_btn.setOnClickListener {
            loadCreateDb()
        }

        get_data_btn.setOnClickListener {
            getDataDb()
        }

        clear_db_button.setOnClickListener {
            clearDb()
        }

        delete_db_button.setOnClickListener{
            deleteDb()
        }

        back_button.setOnClickListener {
            back()
        }

        getDbList()
    }

    fun loadCreateDb(){

        if(db_name_et.text.toString() != ""){

            ExampleDb.dbName = db_name_et.text.toString()

            viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MainViewModel::class.java)

            loadedCreatedUiState()

            getDbList()

            viewModel.getExample.observe(this, Observer {
                data = it
            })

        }else{
            Toast.makeText(this,"Empty db name",Toast.LENGTH_SHORT).show()
        }
    }

    fun getDataDb(){
        viewModel.getExample.observe(this, Observer {
            data = it
            data_tv.setText(data.toString())
        })
    }

    fun clearDb(){
        viewModel.deleteAll
        viewModel.getExample.observe(this, Observer {
            data = it
            println(data.toString())
            data_tv.setText(data.toString())
        })
    }

    fun deleteDb(){
        this.deleteDatabase(ExampleDb.dbName)
        ExampleDb.INSTANCE = null
        loadCreateUiState()
        getDbList()
    }

    fun back(){
        ExampleDb.INSTANCE = null
        loadCreateUiState()
        getDbList()
    }

    fun allInvisibleForDbListLoading(){
        get_data_btn.visibility = View.INVISIBLE
        clear_db_button.visibility = View.INVISIBLE
        delete_db_button.visibility = View.INVISIBLE
        load_create_db_btn.visibility = View.INVISIBLE
        back_button.visibility = View.INVISIBLE
    }

    fun loadCreateUiState(){
        get_data_btn.visibility = View.INVISIBLE
        clear_db_button.visibility = View.INVISIBLE
        delete_db_button.visibility = View.INVISIBLE
        load_create_db_btn.visibility = View.VISIBLE
        back_button.visibility = View.INVISIBLE
        data_tv.setText("")
    }

    fun loadedCreatedUiState(){
        get_data_btn.visibility = View.VISIBLE
        clear_db_button.visibility = View.VISIBLE
        delete_db_button.visibility = View.VISIBLE
        load_create_db_btn.visibility = View.INVISIBLE
        back_button.visibility = View.VISIBLE
        db_name_et.setText("")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDbList(){
        dbList.clear()
        dbList_tv.setText("Db List Loading...")
        allInvisibleForDbListLoading()
        Handler().postDelayed({
            for (i in 0 until this.databaseList().size){
                val rem = i % 3
                if(rem == 0){
                    dbList.add(this.databaseList()[i])
                }
            }
            dbList_tv.setText(dbList.toString())

            if(firstOrLoadedFlag == false){
                load_create_db_btn.visibility = View.VISIBLE
                selectedDb_tv.setText("Selected DB: ")
                firstOrLoadedFlag = true
            }else{
                loadedCreatedUiState()
                selectedDb_tv.setText("Selected DB: " + ExampleDb.dbName)
                firstOrLoadedFlag = false
            }
        },2000)
    }
}
