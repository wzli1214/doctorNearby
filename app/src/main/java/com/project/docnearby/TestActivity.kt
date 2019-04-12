package com.project.docnearby

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class TestActivity: AppCompatActivity() {

    private lateinit var searchTextDoc: EditText
    private lateinit var searchTextSpecword: EditText
    private lateinit var search: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        searchTextDoc=findViewById(R.id.searchTextDoc)
        searchTextSpecword=findViewById(R.id.searchTextSpec)
        search=findViewById(R.id.search_button)




        search.setOnClickListener {

            val inputtedDocName: String = searchTextDoc.text.toString().trim()
            val inputtedSpeciality: String = searchTextSpecword.text.toString().trim()

            //TODO to send inputted data to the next screen
            //TODO to find out the location using geocoding
            val intent: Intent = Intent(this, DoctorActivity::class.java)
            startActivity(intent)
        }

    }

}