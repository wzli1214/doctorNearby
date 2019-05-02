package com.project.docnearby

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText

class PracSearchActivity : AppCompatActivity(){

    private lateinit var searchTextPrac: EditText
    private lateinit var practiceSearchButton: Button

    private val textWatcher: TextWatcher = object : TextWatcher {



        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            val inputtedPrac: String = searchTextPrac.text.toString().trim()

            val enablebutton: Boolean = (inputtedPrac.isNotEmpty())



            practiceSearchButton.isEnabled=enablebutton


        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practicesearch)

        //        actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.PracticeSearch)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


        searchTextPrac = findViewById(R.id.typePractice)
        practiceSearchButton = findViewById(R.id.pracSearchButton)


        searchTextPrac.addTextChangedListener(textWatcher)

        //click the practiceSearchButton, will start practice activity, and show as a list
        practiceSearchButton.setOnClickListener {
            Log.d("PracSearchActivity", "Practice Search Clicked")

            val inputtedPractice: String = searchTextPrac.text.toString().trim()



            val intentPractice: Intent = Intent(this@PracSearchActivity, PracticeActivity::class.java)


            val bundlePass2 = Bundle()
            bundlePass2.putString("inputtedPractice", inputtedPractice)

            intentPractice.putExtras(bundlePass2)
            startActivity(intentPractice)


        }






    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}