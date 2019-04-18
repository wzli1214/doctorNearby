package com.project.docnearby

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.jetbrains.anko.doAsync
import java.io.IOException
import java.util.*

class SearchActivity: AppCompatActivity() {

    private lateinit var searchTextLoc: EditText
    private lateinit var searchTextSymptom: EditText
    private lateinit var search: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchTextLoc=findViewById(R.id.searchLocation)
        searchTextSymptom=findViewById(R.id.searchTextSympton)
        search=findViewById(R.id.search_button)



        //Affirm the location first, which could be found
        //on the map, and could be geocode into lat and lng.
        search.setOnClickListener {
            Log.d("SearchActivity","Search button clicked")
            //      Run the Geocoder in the background thread.

            val inputtedSymptom: String = searchTextSymptom.text.toString().trim()


            doAsync {

                val geocoder = Geocoder(this@SearchActivity, Locale.getDefault())


                try{
                    val results: List<Address> = geocoder.getFromLocationName(
                        searchTextLoc.text.toString(), 10
                    )

                    val addr: MutableList<String> = mutableListOf()

                    results.forEach { curr ->
                        addr.add(curr.getAddressLine(0))

                    }



                    if (addr.isEmpty()) {
                        Log.d("SearchActivity", "No address")

                        addr.add("No match address, please change the address")

                    } else {
                        Log.d("SearchActivity", "1 or more addresses")

                    }

                    // Display the radio button dialog in the UI thread
                    runOnUiThread {


                        // select_dialog_singlechoice is a pre-defined XML layout for a RadioButton row
                        val arrayAdapter =
                            ArrayAdapter<String>(this@SearchActivity, android.R.layout.select_dialog_singlechoice)
                        arrayAdapter.addAll(addr)

                        AlertDialog.Builder(this@SearchActivity)
                            .setTitle("Please affirm the location").setAdapter(arrayAdapter) { dialog, which ->

                                if (addr[which] == "No match address, please change the address") {
                                    dialog.dismiss()
                                } else {


                                    println("chose" + " " + addr[which])
                                    println("list which" + "" + results.get(which))

                                    val latChose = results.get(which).latitude
                                    val lngChose = results.get(which).longitude


                                    Log.d("SearchActivity", "Radio button Clicked")
                                    val intentDoctor: Intent = Intent(this@SearchActivity, DoctorActivity::class.java)


//                                  //Pass the lat and lng to the next activity.
                                    var bundlePass = Bundle()
                                    bundlePass.putString("addChose", addr[which])

                                    bundlePass.putDouble("latChose", latChose)
                                    bundlePass.putDouble("lngChose", lngChose)
                                    bundlePass.putString("SymptomToPass", inputtedSymptom)

                                    intentDoctor.putExtras(bundlePass)

                                    startActivity(intentDoctor)




                                    Toast.makeText(this@SearchActivity, "You picked: ${addr[which]}", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            }
                            .setNegativeButton("Cancel") { dialog, which ->
                                dialog.dismiss()
                            }
                            .show()

                    }


                } catch(e: IOException){
                    runOnUiThread {
                        // Runs if we have an error
                        Toast.makeText(this@SearchActivity, "Error of retrieving address", Toast.LENGTH_LONG).show()

                    }

                }




            }




        }



    }

}