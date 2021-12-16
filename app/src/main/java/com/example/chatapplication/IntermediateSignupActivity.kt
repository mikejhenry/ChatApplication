package com.example.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class IntermediateSignupActivity : AppCompatActivity() {

    private lateinit var btn_student: Button
    private lateinit var btn_faculty: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intermediate_signup)

        btn_student = findViewById(R.id.btn_student)
        btn_faculty = findViewById(R.id.btn_faculty)

        btn_student.setOnClickListener{
            val intent = Intent(this, SignUp::class.java)
            intent.putExtra("isStudent", true)
            //val intent = Intent(this, IntermediateSignupActivity::class.java)
            startActivity(intent)
        }


        btn_faculty.setOnClickListener{
            val intent = Intent(this, FacultySignUp::class.java)
            intent.putExtra("isStudent", false)
            //val intent = Intent(this, IntermediateSignupActivity::class.java)
            startActivity(intent)
        }

    }
}