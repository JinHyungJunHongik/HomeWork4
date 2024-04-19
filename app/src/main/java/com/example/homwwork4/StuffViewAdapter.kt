package com.example.homwwork4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

//뷰 안에 버튼들 클릭 시 사용할 인터페이스 함수들
interface onClickEvent{
   fun onItemClick(item: Stuff)
   fun onRemoveItem(item: Stuff)
}

class StuffViewAdapter(private val data: MutableList<Stuff>, private val eventListener: onClickEvent) : RecyclerView.Adapter<StuffViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val img : ImageView
        val name : TextView
        val price : TextView
        val address : TextView
        val numberOfLike : TextView
        val numberOfFeedBack : TextView
        val stuffContainer : ConstraintLayout
        val close : ImageView
        init {
            img = view.findViewById(R.id.img_stuffImage)
            name = view.findViewById(R.id.tv_stuffName)
            price = view.findViewById(R.id.tv_stuffprice)
            address = view.findViewById(R.id.tv_stuffaddress)
            numberOfLike = view.findViewById(R.id.tv_numslike)
            numberOfFeedBack = view.findViewById(R.id.tv_numsfeedback)
            stuffContainer = view.findViewById(R.id.stuff_data)
            close = view.findViewById(R.id.img_btnclose)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stuff, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(data[position].pullImg())
        holder.name.text = data[position].pullName()
        holder.price.text = data[position].priceCount()
        holder.address.text = data[position].pullAddress()
        holder.numberOfLike.text = data[position].pullLike().toString()
        holder.numberOfFeedBack.text = data[position].pullComments().size.toString()
        holder.stuffContainer.setOnClickListener {
            eventListener.onItemClick(data[position])
        }
        holder.close.setOnClickListener {
            eventListener.onRemoveItem(data[position])
        }
    }
}