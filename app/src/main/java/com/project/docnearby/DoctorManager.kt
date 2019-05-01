package com.project.docnearby

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class DoctorManager {


    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        // This sets network timeouts (in case the phone can't connect
        // to the server or the server is down)
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)

        // This causes all network traffic to be logged to the console
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        okHttpClient = builder.build()
    }



    fun retrieveDoctor(
        apiKey: String,
        latChose: Double,
        lngChose: Double,
        symptChose: String,
        successCallback: (List<Doctor>) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {
        // Data setup
        val lat = latChose
        val lng = lngChose
        val miles = 100
        val symptomSearch = symptChose

        var request = Request.Builder()
            .url("https://api.betterdoctor.com/2016-03-01/doctors?location=$lat%2C$lng%2C$miles&skip=0&limit=10&user_key=$apiKey")
            .header("user_key", apiKey)
            .build()

        //If the user type a specific query, then do more advanced search with query, lat and lng.
        if(symptomSearch != null){
            // Building the request, passing the OAuth token as a header
                request = Request.Builder()
                .url("https://api.betterdoctor.com/2016-03-01/doctors?query=$symptomSearch&location=$lat%2C$lng%2C$miles&skip=0&limit=10&user_key=$apiKey")
                .header("user_key", apiKey)
                .build()

        }


        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Same error handling to last time
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                // Similar success / error handling to last time
                val docs = mutableListOf<Doctor>()
                val responseString = response.body()?.string()

                if (response.isSuccessful && responseString != null) {
                    val incidents = JSONObject(responseString).getJSONArray("data")
                    for (i in 0 until incidents.length()) {
                        val curr = incidents.getJSONObject(i)

                        val docFirstName = curr.getJSONObject("profile").getString("first_name")
                        val docLastName = curr.getJSONObject("profile").getString("last_name")

                        val docFullName = docFirstName + " " + docLastName

                        val docImage = curr.getJSONObject("profile").getString("image_url")

                        //The bio has too much words, thus we retrieved the first paragraph.
                        val docBio = curr.getJSONObject("profile").getString("bio").split("\n")
                        println("see the bios "+ docBio[0] )

                        //The bio is too much words.
//                        val split = docBio.split("\\n")
//                        val firstParagraph = split.get(0)
//                        println("brief bio "+ firstParagraph)

                        val docPhone = curr.getJSONArray("practices").getJSONObject(0)
                            .getJSONArray("phones").getJSONObject(0).getString("number")



                        val healthCenter = curr.getJSONArray("practices").getJSONObject(0)
                            .getString("name")

                        println("------------------------")
                        println(healthCenter)

                        val docStreet = curr.getJSONArray("practices").getJSONObject(0)
                            .getJSONObject("visit_address").getString("street")

                        val docState = curr.getJSONArray("practices").getJSONObject(0)
                            .getJSONObject("visit_address").getString("state")

                        val docZip = curr.getJSONArray("practices").getJSONObject(0)
                            .getJSONObject("visit_address").getString("zip")

                        val docAddress = docStreet + ", " + docState + ", " + docZip




                        docs.add(
                            Doctor(
                                name = docFullName,
                                //The bio has too much words, thus we retrieved the first paragraph.
                                docDescription = docBio[0],
                                docPhone = "Phone Number: " + "\n" + docPhone,
                                centerName = "Health Center Name: " + "\n" + healthCenter,
                                doctorAddress = "Address: " +  "\n" + docAddress,
                                iconUrl = docImage




                            )
                        )
                    }
                    successCallback(docs)

                } else {
                    // Invoke the callback passed to our [retrieveDoctor] function.
                    errorCallback(Exception("Search doctor call failed"))

                }
            }
        })
    }

}