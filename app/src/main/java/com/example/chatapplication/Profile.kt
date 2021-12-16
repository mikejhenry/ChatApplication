package com.example.chatapplication

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.example.chatapplication.R
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.chatapplication.Login
import com.google.firebase.database.FirebaseDatabase
import android.widget.TextView
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.example.chatapplication.Student
import com.google.firebase.database.DatabaseError
import android.widget.Toast

class Profile : AppCompatActivity() {
    //THIS PROFILE SHOULD BE FOR STUDENTS ONLY, WE NEED TO MAKE A FACULTY PROFILE
    private var user: FirebaseUser? = null
    private var reference: DatabaseReference? = null
    private var userID: String? = null
    private var logOut: Button? = null
    private var mainChat: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        logOut = findViewById<View>(R.id.btn_logOut) as Button
        mainChat = findViewById<View>(R.id.btn_mainChat) as Button
        logOut!!.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@Profile, Login::class.java))
        }

        mainChat!!.setOnClickListener {
            startActivity(Intent(this@Profile, MainActivity::class.java))
        }
        user = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("student")
        userID = user?.getUid()
        val greetingTextView = findViewById<View>(R.id.greeting) as TextView
        val fullNameTextView = findViewById<View>(R.id.fullName) as TextView
        val emailTextView = findViewById<View>(R.id.emailAddress) as TextView
        val yearTextView = findViewById<View>(R.id.year) as TextView
        reference!!.child(userID!!).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val studentProfile = snapshot.getValue(Student::class.java)
                if (studentProfile != null) {
                    val firstName = studentProfile.firstname
                    val lastName = studentProfile.lastname
                    val fullName = "$firstName $lastName"
                    val email = studentProfile.email
                    val year = studentProfile.year
                    greetingTextView.text = "Welcome. $fullName!"
                    fullNameTextView.text = fullName
                    emailTextView.text = email
                    yearTextView.text = year
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Profile, "Something wrong happened!", Toast.LENGTH_LONG).show()
            }
        })
    }
}