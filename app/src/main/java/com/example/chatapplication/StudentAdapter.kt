package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth


class StudentAdapter(val context: Context, var studentList: ArrayList<Student>):
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder{
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

        val currentStudent = studentList[position]
        holder.textName.text = currentStudent.firstname

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)

            intent.putExtra("name", currentStudent.firstname)
            intent.putExtra("uid", currentStudent.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return studentList.size
    }


    class  StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
    }

}