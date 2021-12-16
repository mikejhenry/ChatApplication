package com.example.chatapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.chatapplication.R.layout.activity_sign_up

class SignUp : AppCompatActivity() {

    private lateinit var edtfirstname: EditText
    private lateinit var edtLastname: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtId: EditText
    private lateinit var edtRoll: EditText
    private lateinit var edtYear: EditText

    private lateinit var btnSignup: Button
    private lateinit var stdRadioBtns: RadioGroup
    private lateinit var radioBtnStudent: RadioButton
    private lateinit var radioBtnFaculty: RadioButton
    private lateinit var student: SignUp

    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private var id: String? = null
    private var roll: String? = null
    private lateinit var stringArray: ArrayList<String?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_sign_up)



        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtfirstname = findViewById(R.id.edt_frstname)
        edtLastname = findViewById(R.id.edt_lstname)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtId = findViewById(R.id.edt_student_id_no)
        edtRoll = findViewById(R.id.edt_rollnumber)
        btnSignup = findViewById(R.id.btn_signup)
        edtYear = findViewById(R.id.edt_year)
        //stdRadioBtns = findViewById(R.id.radioGroup)
        //radioBtnStudent = findViewById(R.id.std_Btn)
        //radioBtnFaculty =  findViewById(R.id.faculty_Btn)





        //val spinner: Spinner = findViewById(R.id.edt_year)
      //  spinner.onItemSelectedListener = this

        btnSignup.setOnClickListener {


            val firstname = edtfirstname.text.toString()
            val lastname = edtLastname.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val id = edtId.text.toString()
            val roll = edtRoll.text.toString()
            val year = edtYear.text.toString()

            stringArray = arrayListOf(firstname,lastname,year,email,password,id,roll)


            for(string in stringArray){

                if(string == null  || string.isEmpty()){

                    when(string){
                        firstname -> Toast.makeText(this, "Fill In First The Name Field",Toast.LENGTH_LONG).show()
                        lastname ->   Toast.makeText(this, "Fill In Last The Name Field",Toast.LENGTH_LONG).show()
                        email  ->   Toast.makeText(this, "Fill In The Email Field",Toast.LENGTH_LONG).show()
                        password -> Toast.makeText(this, "Fill In The Password Field",Toast.LENGTH_LONG).show()
                      //  spinner.onItemClickListener.toString() ->   Toast.makeText(this, "Select An Option From DropDown Menu",Toast.LENGTH_LONG).show()
                    }
                }
            }


            if("@hartford.edu" !in email){
                println("Working")
                Log.d("Check"," $email  is working")
                Toast.makeText(applicationContext,"You Must Use @jsu.edu As The Email", Toast.LENGTH_LONG).show()
            }
            else if(edtYear.text.toString() == ""){
                Log.d("Check","${edtfirstname.text.toString().isEmpty()} is working")
                Toast.makeText(this,"Select A Year", Toast.LENGTH_LONG).show()
            }
            else{
                signUp(firstname,lastname, id!!, roll!!,edtYear.text.toString(),email,password)
            }
        }


    }




    private fun signUp(firstname:String, lastname: String, id: String, roll: String,
                       year: String, email:String, password: String) {



        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(firstname,lastname,id,roll,year,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {

                    Toast.makeText(this@SignUp,"Some error happen", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun addUserToDatabase(firstname: String, lastname: String, id: String,
                                  roll: String, year: String, email:String, uid:String) {

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("student").child(uid).setValue(Student(firstname,lastname,id,roll,year,email,uid))
    }
/*
    fun onClick(view: android.view.View) {


        if(view is RadioButton){
            val checked = view.isChecked


            when(view.getId()){

                R.id.std_Btn ->

                    if(checked){


                        println("THIS WHAT IS PRINTING 1${view.isChecked}")
                        Log.d("Checking","This is printing1 ${view.isChecked}")
                        Toast.makeText(this@SignUp,"You Already On The Student Page", Toast.LENGTH_SHORT).show()

                    }

                R.id.faculty_Btn ->


                    if(checked){

                        println("THIS WHAT IS PRINTING2 ${view.isChecked}")
                        Log.d("Checking","This is printing2 ${view.isChecked}")
                        val intent = Intent(this@SignUp, FacultySignUp::class.java)
                        startActivity(intent)
                    }
            }
        }


    }

 */



    private fun idGenerator(): Int {

        val range = 10000..50000

        return range.random()

    }

}

