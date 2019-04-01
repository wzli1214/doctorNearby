package com.project.docnearby

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText


class SignUpActivity: AppCompatActivity() {


    private lateinit var username1: EditText
    private lateinit var password1: EditText
    private lateinit var password2: EditText
    private lateinit var signup1: Button



    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            val inputtedpassword1: String = password1.text.toString().trim()
            val inputtedpassword2: String = password2.text.toString().trim()
            val enablebutton = false

            if (inputtedpassword1==inputtedpassword2){

                signup1.isEnabled=enablebutton
            }

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        username1=findViewById(R.id.username1)
        password1=findViewById(R.id.password1)
        password2=findViewById(R.id.password2)
        signup1=findViewById(R.id.signup1)



        password1.addTextChangedListener(textWatcher)
        password2.addTextChangedListener(textWatcher)

    }
}