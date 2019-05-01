package com.project.docnearby

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var progressbar: ProgressBar
    private lateinit var remember: Switch
    private lateinit var rememberPassword: Switch


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics



    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            val inputtedusername: String = username.getText().toString().trim()
            val inputedpassword: String = password.text.toString().trim()
            val enablebutton: Boolean = inputtedusername.isNotEmpty()&& inputedpassword.isNotEmpty()

            login.isEnabled=enablebutton
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)



        username =findViewById(R.id.PracName)
        password=findViewById(R.id.password)
        login=findViewById(R.id.login)
        progressbar=findViewById(R.id.progressBar)
        signup=findViewById(R.id.signup)
        remember=findViewById(R.id.remember)
        rememberPassword=findViewById(R.id.rememberPass)


        username.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)

        //Remember the account
        remember.setOnCheckedChangeListener { view, isChecked ->

            val editor = getSharedPreferences("save_UserAccount", Context.MODE_PRIVATE).edit()

            // If isChecked, remember the typed information for the next time
            if(isChecked){
                Log.d("MainActivity", "Remember checked")
                val userSave = username.text.toString()

                editor.putString("userSave", userSave)

                editor.apply()

            }

            // If click unChecked, forget the typed information.
            else {
                editor.clear()
                editor.apply()
            }
        }


        //Remember the account
        rememberPassword.setOnCheckedChangeListener { view, isChecked ->

            val editor = getSharedPreferences("save_UserPassword", Context.MODE_PRIVATE).edit()

            // If isChecked, remember the typed information for the next time
            if(isChecked){
                Log.d("MainActivity", "RememberPassword checked")
                val passwordSave = password.text.toString()

                editor.putString("passwordSave", passwordSave)

                editor.apply()

            }

            // If click unChecked, forget the typed information.
            else {
                editor.clear()
                editor.apply()
            }
        }




        //Retrieve the account if remembered.
        val record = getSharedPreferences("save_UserAccount", 0)
        username.setText(record.getString("userSave", ""))

        //If the email is being remembered,
        // the switch should be turned on by default."
        if(username.text.toString() != ""){
            remember.setChecked(true)
        }


        //Retrieve the password information if remembered.
        val recordPass = getSharedPreferences("save_UserPassword", 0)
        password.setText(recordPass.getString("passwordSave", ""))

        //If the email and/or password are being remembered,
        // the switches should be turned on by default."
        if(password.text.toString() != ""){
            rememberPassword.setChecked(true)
        }


        //SignUp
        signup.setOnClickListener {

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

        //Login
        login.setOnClickListener {
            progressbar.visibility = View.VISIBLE

            val inputtedUsername: String = username.text.toString().trim()
            val inputtedPassword: String = password.text.toString().trim()

            firebaseAuth.signInWithEmailAndPassword(
                inputtedUsername,
                inputtedPassword
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAnalytics.logEvent("login_success", null)

                    val currentUser: FirebaseUser? = firebaseAuth.currentUser
                    Toast.makeText(
                        this,
                        "Logged in as: ${currentUser!!.email}",
                        Toast.LENGTH_LONG
                    ).show()

                    //Advance to the next screen

                    val intent: Intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)

                } else {
                    val exception = task.exception
                    Toast.makeText(
                        this,
                        "Failed to login: $exception",
                        Toast.LENGTH_LONG
                    ).show()

                    val reason: String = if (exception is FirebaseAuthInvalidCredentialsException){
                        "invalid_credentials"
                    } else {
                        "generic_failure"
                    }

                    val bundle = Bundle()
                    bundle.putString("error_reason", reason)

                    firebaseAnalytics.logEvent("login_failed", bundle)

                }


                }
            }


        }


    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart called")
    }


    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "onResume called")

    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop called")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy called")

    }


}
