package com.project.docnearby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class DoctorAdapter constructor(val doctor: List<Doctor>): RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor =doctor[position]

       holder.usernameTextView.text = currentDoctor.name

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_doctor, parent, false)
        return ViewHolder(view)
    }

    //Todo
    override fun getItemCount(): Int = doctor.size



    class ViewHolder constructor(view: View): RecyclerView.ViewHolder(view){
        val usernameTextView: TextView = view.findViewById(R.id.doctorName)

    }
}