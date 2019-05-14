package com.project.docnearby

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

class PracticeActivity: AppCompatActivity()  {

    private val practiceManager: PracticeManager = PracticeManager()
    private lateinit var recyclerView: RecyclerView
    private val practiceList: MutableList<Practice> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Use the same layout as activity_doctor
        setContentView(R.layout.activity_doctor)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.SearchResults)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)


        //Get the inputtedPractice from the last activity
        val bundle = getIntent().extras
        val inputtedPractice = bundle.getString("inputtedPractice")
        println("testPass Practice Name " + inputtedPractice)


        practiceManager.retrievePractice(
            apiKey = getString(R.string.doc_key),
            practiceName = inputtedPractice,
            successCallback = {practices ->
                runOnUiThread{
                    if(practices.isNotEmpty()){
                        //Create the adapter and assign it to the RecyclerView
                        recyclerView.adapter = PracticeAdapter(practices)

                    }

                    else{
                        //This makes sure no blank screen is displayed and shows toast to inform user
                        Toast.makeText(this@PracticeActivity, "No practice to show", Toast.LENGTH_LONG).show()

                    }
                }


            },

            errorCallback = {
                runOnUiThread{
                    //Runs if we have an error
                    Toast.makeText(this@PracticeActivity, "Error of retrieving Practice list",
                        Toast.LENGTH_LONG).show()
                }
            }
        )

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}




