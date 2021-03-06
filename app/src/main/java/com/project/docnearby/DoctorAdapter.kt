package com.project.docnearby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DoctorAdapter constructor(val doctor: List<Doctor>): RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_docnew, parent, false)
        return ViewHolder(view)
    }

    //Todo
    override fun getItemCount(): Int = doctor.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDoctor =doctor[position]

        holder.doctorNameTextView.text = currentDoctor.name
        holder.doctorBioTextView.text = currentDoctor.docDescription
        holder.doctorPhoneTextView.text = currentDoctor.docPhone
        holder.healthCenterTextView.text = currentDoctor.centerName
        holder.doctorAddressTextView.text = currentDoctor.doctorAddress

        Picasso.get().setIndicatorsEnabled(true)

        Picasso.get()
            .load(currentDoctor.iconUrl)
            .into(holder.iconImageView)

    }


    class ViewHolder constructor(view: View): RecyclerView.ViewHolder(view){
        val doctorNameTextView: TextView = view.findViewById(R.id.PracName)
        //Doctor bio
        val doctorBioTextView: TextView = view.findViewById(R.id.docDescription)
        val doctorPhoneTextView: TextView = view.findViewById(R.id.phone)
        val healthCenterTextView: TextView = view.findViewById(R.id.centerAddress)
        val doctorAddressTextView: TextView = view.findViewById(R.id.docAddress)
        val iconImageView: ImageView = view.findViewById(R.id.docImage)




    }
}