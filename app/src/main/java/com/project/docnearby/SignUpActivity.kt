package com.project.docnearby

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpActivity: AppCompatActivity() {


    private lateinit var username1: EditText
    private lateinit var password1: EditText
    private lateinit var password2: EditText
    private lateinit var signup1: Button

    private lateinit var firebaseAuth: FirebaseAuth




    private val textWatcher: TextWatcher = object : TextWatcher {



        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            val inputtedpassword1: String = password1.text.toString().trim()
            val inputtedpassword2: String = password2.text.toString().trim()
            val enablebutton: Boolean = (inputtedpassword1.isNotEmpty()
                    && inputtedpassword2.isNotEmpty()) && (inputtedpassword1==inputtedpassword2)



            signup1.isEnabled=enablebutton


        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Sign Up"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()


        username1=findViewById(R.id.username1)
        password1=findViewById(R.id.password1)
        password2=findViewById(R.id.password2)
        signup1=findViewById(R.id.signup1)



        password1.addTextChangedListener(textWatcher)
        password2.addTextChangedListener(textWatcher)


        signup1.setOnClickListener {
            val inputtedUsername1: String = username1.text.toString().trim()
            val inputtedPassword1: String = password2.text.toString().trim()

            firebaseAuth.createUserWithEmailAndPassword(
                inputtedUsername1, inputtedPassword1).addOnCompleteListener { task ->
                // If Sign Up is successful, Firebase automatically logs
                // in as that user too (e.g. currentUser is set)
                if (task.isSuccessful){
                    val currentUser: FirebaseUser? = firebaseAuth.currentUser
                    currentUser?.sendEmailVerification()
                    Toast.makeText(
                        this,
                        "Registered as: ${currentUser!!.email}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val exception = task.exception
                    Toast.makeText(
                        this,
                        "Failed to register: $exception",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}