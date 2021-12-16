package com.example.chatapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
//import com.example.jacksonstateuniversity.R.layout.activity_faculty_sign_up
import com.example.chatapplication.R.layout.activity_faculty_sign_up
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FacultySignUp : AppCompatActivity() {


    private lateinit var edtfirstname: EditText
    private lateinit var edtLastname: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtRepeatPassword: EditText
    private lateinit var edtId: EditText
    private lateinit var edtRoll: EditText
    private lateinit var edtDepartment: EditText


    private lateinit var btnSignup: Button
    private lateinit var stdRadioBtns: RadioGroup


    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    private var id: String? = null
    private var roll: String? = null
    private lateinit var stringArray: ArrayList<String?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_faculty_sign_up)



        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtfirstname = findViewById(R.id.edt_name)
        edtLastname = findViewById(R.id.edt_lastname)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtRepeatPassword = findViewById(R.id.edt_repeat_password)
        edtRoll = findViewById(R.id.edt_rollnumber)
        btnSignup = findViewById(R.id.btn_signup)
        //stdRadioBtns = findViewById(R.id.radioGroup)
        edtDepartment = findViewById(R.id.edt_dept)


     //   val spinner: Spinner?= findViewById(R.id.edt_dept)
       // spinner?.onItemSelectedListener = this

        btnSignup.setOnClickListener {


            val firstname = edtfirstname.text.toString()
            val lastname = edtLastname.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val roll = edtRoll.text.toString()
            val department = edtDepartment.text.toString()

            stringArray = arrayListOf(firstname,lastname,department,email,password,roll)


            for(string in stringArray){

                if(string == null  || string.isEmpty()){

                    when(string){
                        firstname -> Toast.makeText(this, "Fill In First The Name Field",Toast.LENGTH_LONG).show()
                        lastname ->   Toast.makeText(this, "Fill In Last The Name Field",Toast.LENGTH_LONG).show()
                        email  ->   Toast.makeText(this, "Fill In The Email Field",Toast.LENGTH_LONG).show()
                        password -> Toast.makeText(this, "Fill In The Password Field",Toast.LENGTH_LONG).show()
                       // spinner?.onItemClickListener.toString() ->   Toast.makeText(this, "Select An Option From DropDown Menu",Toast.LENGTH_LONG).show()
                    }
                }
            }


            if(!email.endsWith("@hartford.edu")){
                Toast.makeText(applicationContext,"Please use an email address with @hartford.edu as the TLD", Toast.LENGTH_LONG).show()
            }
            else if(edtDepartment.text.toString() == ""){
                Toast.makeText(this,"Please enter your department", Toast.LENGTH_LONG).show()
            } else if(edtPassword.text.toString() != edtRepeatPassword.text.toString()){
                Toast.makeText(this,"The passwords you entered do not match.", Toast.LENGTH_LONG).show()
            }
            else{
                signUp(firstname,lastname, roll!!,department,email,password)
            }
        }


    }




    private fun signUp(firstname:String, lastname: String, roll: String,
                       department: String, email:String, password: String) {



        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(firstname,lastname,roll,department,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@FacultySignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {

                    Toast.makeText(this@FacultySignUp,"Some error happen", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun addUserToDatabase(firstname: String, lastname: String,
                                  roll: String, department: String, email:String, uid:String) {

        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("Faculty Members").child(uid).setValue(Faculty(firstname,lastname,id,roll,department,email,uid))
    }




    private fun idGenerator(): Int {

        val range = 10000..50000

        return range.random()

    }




}