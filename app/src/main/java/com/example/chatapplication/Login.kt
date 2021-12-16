package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var forgotPassowrd: TextView
    private lateinit var edtPassword: EditText
    private lateinit var btnLoginStudent: Button
    private lateinit var btnLoginFaculty: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        forgotPassowrd = findViewById(R.id.forgotPassword)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLoginStudent = findViewById(R.id.btn_loginStudent)
        btnLoginFaculty = findViewById(R.id.btn_loginFaculty)
        btnSignUp = findViewById(R.id.btn_signup)

        forgotPassowrd.setOnClickListener{
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener{
            //val intent = Intent(this, SignUp::class.java)
            val intent = Intent(this, IntermediateSignupActivity::class.java)
            startActivity(intent)
        }

        btnLoginStudent.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            loginStudent(email,password);
        }

        btnLoginFaculty.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            loginFaculty(email,password);
        }

    }
    private fun loginStudent(email: String, password: String){
        //logic for logging in user

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val intent = Intent(this@Login, Profile::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "Username/Password combination not found.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun loginFaculty(email: String, password: String){
        //logic for logging in user

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val intent = Intent(this@Login, FacultyProfile::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "Username/Password combination not found.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


}