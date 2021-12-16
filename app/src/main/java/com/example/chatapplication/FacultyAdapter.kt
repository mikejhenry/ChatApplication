package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth


class FacultyAdapter(val context: Context, var facultyList: ArrayList<Faculty>):
    RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder{
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return FacultyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {

        val currentFaculty = facultyList[position]
        holder.textName.text = currentFaculty.firstname

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)

            intent.putExtra("name", currentFaculty.firstname)
            intent.putExtra("uid", currentFaculty.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return facultyList.size
    }


    class  FacultyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
    }

}