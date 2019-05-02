package com.project.docnearby

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.net.URLEncoder


class PracticeManager {

    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        // This sets network timeouts (in case the phone can't connect
        // to the server or the server is down)
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20,TimeUnit.SECONDS)
        builder.writeTimeout(20,TimeUnit.SECONDS)


        // This causes all network traffic to be logged to the console
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        okHttpClient = builder.build()


    }


    fun retrievePractice(
        apiKey: String,
        practiceName: String,
        successCallback: (List<Practice>) -> Unit,
        errorCallback: (Exception) -> Unit
    ){
        //Data set up

        val passPractice = practiceName
        //Convert the inputted practice name into URL version, "%20" expresses a space, instead of "+"
        val PracticeUrl = URLEncoder.encode(passPractice, "utf-8")
        val PracticeUrl2 = PracticeUrl.replace("+","%20")



        println("convert into URL " + PracticeUrl2)




        var request = Request.Builder()
            .url("https://api.betterdoctor.com/" +
                    "2016-03-01/practices?name=$PracticeUrl2&skip=0&limit=5&user_key=$apiKey")
            .header("user_key", apiKey)
            .build()


        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Same error handling to last time
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val pracs = mutableListOf<Practice>()
                val responseString = response.body()?.string()

                if (response.isSuccessful && responseString != null) {
                    val incidents = JSONObject(responseString).getJSONArray("data")
                    for (i in 0 until incidents.length()) {
                        val curr = incidents.getJSONObject(i)
                        val pracName = curr.getString("name")

                        //Get address
                        val pracState = curr.getJSONObject("visit_address").getString("state_long")
                        val pracStreet = curr.getJSONObject("visit_address").getString("street")
                        val pracZip = curr.getJSONObject("visit_address").getString("zip")

                        val pracAddress = pracStreet + ", " + pracState + ", " + pracZip

//                        println("pracAddress "+ pracAddress )

                        val pracPhone: String = curr.getJSONArray("phones").getJSONObject(0)
                            .getString("number") + "(" + curr.getJSONArray("phones").getJSONObject(0)
                            .getString("type") + ")"

//                        println("pracPhone "+ pracPhone)

                        val totalDocs: String = curr.getString("total_doctors")


                        //Todo:
                        //List the doctors of that practice. And the specialities of the doctors.
                        var docInfoToShow: String = ""
                        val pracDoctors = curr.getJSONArray("doctors")



                        for (i in 0..(pracDoctors.length() - 1)) {
                            val item = pracDoctors.getJSONObject(i)

                            val currDocName: String = item.getJSONObject("profile")
                                .getString("first_name") + " " + item.getJSONObject("profile").getString("last_name")

                            val currNpi: String = item.getString("npi")

                            //Specialities

                            val currDocSpec = item.getJSONArray("specialties")

                            //The specialities of this doctor.
                            var allSpecialities: String = ""

                            for(j in 0..(currDocSpec.length() -1)){
                                val itemSpec = currDocSpec.getJSONObject(j)
                                allSpecialities += itemSpec.getString("name") + "; "
                            }


                            docInfoToShow += "Doctor name: " + currDocName + "\n" + "Specialities: " + allSpecialities + "\n" +
                                    "NPI number: " + currNpi + "\n"



                        }

                        println("docList to show " + docInfoToShow)



                        pracs.add(
                            Practice(
                                name = pracName,
                                pracAddress = "Address: " + pracAddress,
                                phone = "Contact: " + pracPhone,
                                totalDocs = "Total Doctors: "+  totalDocs,
                                docInfoOfPrac = docInfoToShow



                            )
                        )


                    }

                    successCallback(pracs)
                }
                else{
                    errorCallback(Exception("Practice search call failed"))

                }

            }



                })

            }

            }



