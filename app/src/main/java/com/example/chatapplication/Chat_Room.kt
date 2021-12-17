package com.example.chatapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.annotations.Nullable
import java.util.HashMap


class Chat_Room : AppCompatActivity() {
    private var btn_send_msg: ImageView? = null
    private var input_msg: EditText? = null
    private var chat_conversation: TextView? = null
    private var user_name: String? = null
    private var room_name: String? = null
    private var root: DatabaseReference? = null
    private var temp_key: String? = null


    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        btn_send_msg = findViewById<View>(R.id.btn_send) as ImageView
        input_msg = findViewById<View>(R.id.msg_input) as EditText
        chat_conversation = findViewById<View>(R.id.textView) as TextView
        user_name = intent.extras!!["user_name"].toString()
        room_name = intent.extras!!["room_name"].toString()
        title = " Room - $room_name"
        root = FirebaseDatabase.getInstance().reference.child(room_name!!)
        btn_send_msg!!.setOnClickListener {
            val map: Map<String, Any> =
                HashMap()
            temp_key = root!!.push().key
            root!!.updateChildren(map)
            val message_root = root!!.child(temp_key!!)
            val map2: MutableMap<String, Any> =
                HashMap()
            map2["name"] = user_name!!
            map2["msg"] = input_msg!!.text.toString()
            message_root.updateChildren(map2)
        }
        root!!.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversation(dataSnapshot)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                append_chat_conversation(dataSnapshot)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private var chat_msg: String? = null
    private var chat_user_name: String? = null

    //where new messages are appended the current conversation
    private fun append_chat_conversation(dataSnapshot: DataSnapshot) {
        val i: Iterator<*> = dataSnapshot.children.iterator()
        while (i.hasNext()) {
            chat_msg = (i.next() as DataSnapshot).value as String?
            chat_user_name = (i.next() as DataSnapshot).value as String?
            chat_conversation!!.append("$chat_user_name : $chat_msg \n")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu2, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            //wrote the logic for logout
            mAuth.signOut()
            val intent = Intent(this@Chat_Room,Login::class.java)
            finish()
            startActivity(intent)
            return true
        } else if(item.itemId == R.id.groupChat){

            return true
        } else if(item.itemId == R.id.facultyList){
            val intent1 = Intent(this@Chat_Room, FacultyActivity::class.java)
            startActivity(intent1)
            return true
        } else if(item.itemId == R.id.studentList){
            val intent1 = Intent(this@Chat_Room, MainActivity::class.java)
            startActivity(intent1)
        }
        return true
    }

}