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
        successCallback: (List<Doctor>) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {
        // Data setup
        val primaryKey = " "

        // Building the request, passing the OAuth token as a header
        val request = Request.Builder()
            .url("https://api.betterdoctor.com/2016-03-01/doctors?location=37.773%2C-122.413%2C100&user_location=37.773%2C-122.413&skip=0&limit=10&")
            .header("user_key", "$primaryKey")
            .build()

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
                       // val linename = curr.getString("LinesAffected")
                        //val desc = curr.getString("Description")

                        docs.add(
                            Doctor(
                                name = "http doctor name "

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