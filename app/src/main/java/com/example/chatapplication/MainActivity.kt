package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var studentList: ArrayList<Student>
    private lateinit var tempStudentList: ArrayList<Student>
    private lateinit var adapter: StudentAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize authorization
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        studentList = ArrayList()
        tempStudentList = ArrayList()
        adapter = StudentAdapter(this, studentList)

        //Pair the recycler view in the layout to the kotlin file
        studentRecyclerView = findViewById(R.id.userRecyclerView)
        //Set layout manager
        studentRecyclerView.layoutManager = LinearLayoutManager(this)
        //Set adapter
        studentRecyclerView.adapter = adapter

        mDbRef.child("student").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                studentList.clear()
                for(postSnapshot in snapshot.children){
                    val currentStudent = postSnapshot.getValue(Student::class.java)

                    if(mAuth.currentUser?.uid != currentStudent?.uid){
                        studentList.add(currentStudent!!)
                    }


                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        tempStudentList.addAll(studentList)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        //Search functionality
        val item = menu?.findItem(R.id.searchAction)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println(newText)
                tempStudentList.clear()
                val searchText = newText?.lowercase(Locale.getDefault())?.trim()
                if (searchText!!.isNotEmpty()) {
                    println(studentList.toString())
                    studentList.forEach {
                        //If user name OR title
                        if (it.firstname?.lowercase(Locale.getDefault())!!.contains(searchText) || it.lastname?.lowercase(Locale.getDefault())!!.contains(searchText)) {

                            //As of now, nothing contains a title
                            // || it.title?.lowercase(Locale.getDefault())!!.contains(searchText))

                            tempStudentList.add(it)
                        }
                    }

                    //update adapter to CUSTOM adapter
                    studentRecyclerView.adapter = adapter
                    adapter.studentList = tempStudentList

                    adapter.notifyDataSetChanged()

                } else {

                    //If text is empty, inflate entire list
                    tempStudentList.clear()
                    tempStudentList.addAll(studentList)
                    studentRecyclerView.adapter!!.notifyDataSetChanged()

                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            //wrote the logic for logout
            mAuth.signOut()
            val intent = Intent(this@MainActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        } else if(item.itemId == R.id.groupChat){
            val intent1 = Intent(this@MainActivity, GroupChatActivity::class.java)
            startActivity(intent1)
            return true
        } else if(item.itemId == R.id.facultyList){
            val intent1 = Intent(this@MainActivity, FacultyActivity::class.java)
            startActivity(intent1)
            return true
        } else if(item.itemId == R.id.studentList){
            return true
        }
        return true
    }
}