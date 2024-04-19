package com.example.homwwork4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


const val RECEIVER = 0
const val SENDER = 1
class ChatViewAdapter(val data: MutableList<Chat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val chat : TextView
        init {
            chat = view.findViewById(R.id.item_tv_receiver_chat)
        }
    }
    class SenderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val chat : TextView
        init {
            chat = view.findViewById(R.id.item_tv_sender_text)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when(viewType){
            RECEIVER -> { view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receiver, parent, false)
                            ReceiverViewHolder(view)}
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chat_sender, parent, false)
                SenderViewHolder(view)
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(data[position].type == RECEIVER){
            holder as ReceiverViewHolder
            holder.chat.text = data[position].str
        }
        else if(data[position].type == SENDER){
            holder as SenderViewHolder
            holder.chat.text = data[position].str
        }
    }
}