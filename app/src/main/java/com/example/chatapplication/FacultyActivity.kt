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


class FacultyActivity : AppCompatActivity() {

    private lateinit var facultyRecyclerView: RecyclerView
    private lateinit var facultyList: ArrayList<Faculty>
    private lateinit var tempFacultyList: ArrayList<Faculty>
    private lateinit var adapter: FacultyAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        facultyList = ArrayList()
        tempFacultyList = ArrayList()
        adapter = FacultyAdapter(this, facultyList)

        facultyRecyclerView = findViewById(R.id.userRecyclerView)
        facultyRecyclerView.layoutManager = LinearLayoutManager(this)
        facultyRecyclerView.adapter = adapter

        mDbRef.child("Faculty Members").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                facultyList.clear()
                for(postSnapshot in snapshot.children){
                    val currentFaculty = postSnapshot.getValue(Faculty::class.java)

                    if(mAuth.currentUser?.uid != currentFaculty?.uid){
                        facultyList.add(currentFaculty!!)
                    }


                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

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
                tempFacultyList.clear()
                val searchText = newText?.lowercase(Locale.getDefault())?.trim()
                if (searchText!!.isNotEmpty()) {
                    println(facultyList.toString())
                    facultyList.forEach {
                        //If user name OR title
                        if (it.firstname?.lowercase(Locale.getDefault())!!.contains(searchText) || it.lastname?.lowercase(Locale.getDefault())!!.contains(searchText)) {

                            //As of now, nothing contains a title


                            tempFacultyList.add(it)
                        }
                    }

                    //update adapter to CUSTOM adapter
                    facultyRecyclerView.adapter = adapter
                    adapter.facultyList = tempFacultyList

                    adapter.notifyDataSetChanged()

                } else {

                    //If text is empty, inflate entire list
                    tempFacultyList.clear()
                    tempFacultyList.addAll(facultyList)
                    facultyRecyclerView.adapter!!.notifyDataSetChanged()

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
            val intent = Intent(this@FacultyActivity,Login::class.java)
            finish()
            startActivity(intent)
            return true
        } else if(item.itemId == R.id.groupChat){
            val intent1 = Intent(this@FacultyActivity,GroupChatActivity::class.java)
            startActivity(intent1)
            return true
        } else if(item.itemId == R.id.studentList) {
            val intent1 = Intent(this@FacultyActivity, MainActivity::class.java)
            startActivity(intent1)
            return true
        } else
        return true
    }
}