package com.project.docnearby

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

class DoctorActivity: AppCompatActivity() {


    private val doctorManger: DoctorManager= DoctorManager()

    private lateinit var recyclerView: RecyclerView
    private val doctorList: MutableList<Doctor> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        recyclerView =findViewById(R.id.recyclerView)

        recyclerView.layoutManager= LinearLayoutManager( this)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.SearchResults)
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


        //  retrieve the lag and lng from SearchActivity
        val bundle = getIntent().extras

        val addPass = bundle.getString("addChose")
        val latPass = bundle.getDouble("latChose")
        val lngPass = bundle.getDouble("lngChose")
        val symptPass = bundle.getString("SymptomToPass")

        println("latPass" + " " + latPass)
        println("lngPass" + " " +lngPass)
        println("symptomPass" + " " + symptPass)

        doctorManger.retrieveDoctor(
            //Pass the apiKey, pass the lat and lng getting from the last intent
            apiKey = getString(R.string.doc_key),
            latChose = latPass,
            lngChose = lngPass,
            symptChose = symptPass,

            successCallback = { doctors ->
                runOnUiThread {

                    if (doctors.isNotEmpty()){
                        // Create the adapter and assign it to the RecyclerView
                        recyclerView.adapter = DoctorAdapter(doctors)
                    }
                    else{
                        //This makes sure No blank screen is displayed and shows toast to inform user
                        Toast.makeText(this@DoctorActivity, "No Doctors to show", Toast.LENGTH_LONG).show()
                    }


                }
            },
            errorCallback = {
                runOnUiThread {
                    // Runs if we have an error
                    Toast.makeText(this@DoctorActivity, "Error retrieving Doctor list", Toast.LENGTH_LONG).show()
                }
            }
        )

}
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}