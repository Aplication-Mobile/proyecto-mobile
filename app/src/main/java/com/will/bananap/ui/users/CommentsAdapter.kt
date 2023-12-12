package com.will.bananap.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.will.bananap.R
import com.will.bananap.entities.cls_Comentarios

class CommentsAdapter(private val commentsList: List<cls_Comentarios>) :
    RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentText: TextView = itemView.findViewById(R.id.commentText)

        // Puedes agregar más TextView u otros elementos según tus necesidades
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.commentText.text = comment.texto
        // Puedes asignar más datos según tus necesidades
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }
}
