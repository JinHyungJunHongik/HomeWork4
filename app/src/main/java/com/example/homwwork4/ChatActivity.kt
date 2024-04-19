package com.example.homwwork4

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homwwork4.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(binding.root)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() {
        val initData = intent.getParcelableExtra<Stuff>("chatData")
        binding = ActivityChatBinding.inflate(layoutInflater)
        binding.tvChatCeller.text = initData!!.pullCeller()
        binding.tvChatName.text = initData.pullName()
        binding.tvChatPrice.text = initData.priceCount()
        binding.imgChatImg.setImageResource(initData.pullImg())
        binding.recyclerChat.adapter = ChatViewAdapter(chatData)
        binding.recyclerChat.layoutManager = LinearLayoutManager(this)
        binding.imgChatBtnback.setOnClickListener {
            finish()
        }
        binding.imgSendchat.setOnClickListener {
            var text = ""
            text = binding.editChat.text.toString()
            chatData.add(Chat(text, SENDER))
            binding.editChat.text.clear()
            (binding.recyclerChat.adapter as ChatViewAdapter).notifyDataSetChanged()
        }
    }
}