package com.example.chatapplication
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
//import android.support.v7.app.AlertDialog
//import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet


class GroupChatActivity : AppCompatActivity() {
    private var addRoom: Button? = null
    private var roomName: EditText? = null
    private var listView: ListView? = null
    private var arrayAdapter: ArrayAdapter<String?>? = null
    private val listOfRooms = ArrayList<String?>()
    private var name: String? = null
    private val root = FirebaseDatabase.getInstance().reference.root.child("Group Chat Rooms")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_chat)
        addRoom = findViewById<View>(R.id.btn_add_room) as Button
        roomName = findViewById<View>(R.id.room_name_edittext) as EditText
        listView = findViewById<View>(R.id.listView) as ListView
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfRooms)
        listView!!.adapter = arrayAdapter
        requestUserName()
        addRoom!!.setOnClickListener {
            val map: MutableMap<String, Any> =
                HashMap()
            map[roomName!!.text.toString()] = ""
            root.updateChildren(map)
        }
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val set: MutableSet<String?> = HashSet()
                val i: Iterator<*> = dataSnapshot.children.iterator()
                while (i.hasNext()) {
                    set.add((i.next() as DataSnapshot).key)
                }
                listOfRooms.clear()
                listOfRooms.addAll(set)
                arrayAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        listView!!.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l ->
                val intent = Intent(applicationContext, Chat_Room::class.java)
                intent.putExtra("room_name", (view as TextView).text.toString())
                intent.putExtra("user_name", name)
                startActivity(intent)
            }
    }

    private fun requestUserName() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Enter name:")
        val input_field = EditText(this)
        builder.setView(input_field)
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialogInterface, i ->
                name = input_field.text.toString()
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
                requestUserName()
            })
        builder.show()
    }

         }
