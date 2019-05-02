package com.project.docnearby

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.text.FieldPosition

class PracticeAdapter constructor(val practice: List<Practice>): RecyclerView.Adapter<PracticeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_practice, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int  = practice.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPractice = practice[position]
        holder.practiceNameTextView.text = currentPractice.name
        holder.practiceAddTextView.text = currentPractice.pracAddress
        holder.practicePhoneTextView.text = currentPractice.phone
        holder.totalDocsTextView.text = currentPractice.totalDocs
        holder.docInfoOfPracTextView.text = currentPractice.docInfoOfPrac


    }

    class ViewHolder constructor(view: View): RecyclerView.ViewHolder(view){
        val practiceNameTextView: TextView = view.findViewById(R.id.PracName)
        val practiceAddTextView: TextView = view.findViewById(R.id.centerAddress)
        val practicePhoneTextView: TextView = view.findViewById(R.id.phone)
        val totalDocsTextView: TextView = view.findViewById(R.id.numOfDocs)
        val docInfoOfPracTextView: TextView = view.findViewById(R.id.docListOfPrac)



    }



}